package com.ruben.backempresa.reserva.application;

import com.ruben.backempresa.correo.application.CorreoService;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;
import com.ruben.backempresa.reserva.domain.Reserva;
import com.ruben.backempresa.reserva.domain.ReservaDisponible;
import com.ruben.backempresa.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import com.ruben.backempresa.reserva.infraestructure.repository.ReservaDisponibleRepository;
import com.ruben.backempresa.reserva.infraestructure.repository.ReservaRepository;
import com.ruben.backempresa.shared.exceptions.BusWithoutEspaceException;
import com.ruben.backempresa.shared.kafka.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService{

    //Como ReservaDisponible se crea cuando llega la primera reserva para una hora/fecha/destino, se cuenta esta y empezamos desde 39 disponibles
    private static final int PLAZAS_BUS = 39;

    private static final String TOPIC = "actualiza";

    @Autowired
    ReservaDisponibleRepository reservaDisponibleRepository;

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    CorreoService correoService;

    @Autowired
    KafkaMessageProducer kafkaMessageProducer;

    @Override
    public ReservaOutputDto hacerReserva(ReservaOutputDto reservaOutputDto) {
        Optional<ReservaDisponible> reservaDisponibleOptional = busquedaReservaDisponible(reservaOutputDto);

        comprobarReservaDisponible(reservaDisponibleOptional, reservaOutputDto);

        Reserva reserva = new Reserva(null, reservaOutputDto.getCiudadDestino(), reservaOutputDto.getNombre()
                , reservaOutputDto.getApellido(), reservaOutputDto.getTelefono(), reservaOutputDto.getEmail()
                , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva());

        reservaRepository.save(reserva);

        //correoService.saveMail(new CorreoInputDto(reservaOutputDto.getCiudadDestino(), reservaOutputDto.getEmail()
         //       , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva()));

        return reservaOutputDto;
    }

    @Override
    public ReservaOutputDto hacerReservaKafka(ReservaOutputDto reservaOutputDto) {
        Optional<ReservaDisponible> reservaDisponibleOptional = busquedaReservaDisponible(reservaOutputDto);

        comprobarReservaDisponible(reservaDisponibleOptional, reservaOutputDto);

        Reserva reserva = new Reserva(null, reservaOutputDto.getCiudadDestino(), reservaOutputDto.getNombre()
                , reservaOutputDto.getApellido(), reservaOutputDto.getTelefono(), reservaOutputDto.getEmail()
                , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva());

        reservaRepository.save(reserva);
        kafkaMessageProducer.sendMessage(TOPIC,reservaOutputDto);
        //correoService.saveMail(new CorreoInputDto(reservaOutputDto.getCiudadDestino(), reservaOutputDto.getEmail()
        //       , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva()));
        return reservaOutputDto;
    }

    @Override
    public void escucharReservaKafka(ReservaOutputDto reservaOutputDto){
        hacerReserva(reservaOutputDto);
    }

    private Optional<ReservaDisponible> busquedaReservaDisponible(ReservaOutputDto reservaOutputDto){
        List<ReservaDisponible> listaReservasCiudad = reservaDisponibleRepository.findByCiudadDestino(reservaOutputDto.getCiudadDestino());
        return listaReservasCiudad.stream().filter(reservaDisponible1 ->
                        reservaDisponible1.getHoraSalida().equals(reservaOutputDto.getHoraReserva()) &&
                                reservaDisponible1.getFechaSalida().compareTo(reservaOutputDto.getFechaReserva()) == 0)
                .findAny();
    }

    private void comprobarReservaDisponible(Optional<ReservaDisponible> reservaDisponibleOptional, ReservaOutputDto reservaOutputDto){
        if(reservaDisponibleOptional.isPresent()){
            ReservaDisponible reservaDisponible = reservaDisponibleOptional.get();
            if(reservaDisponible.getNumeroPlazas() == 0){
                throw new BusWithoutEspaceException("No hay plazas libres en el bus especificado");
            }else{
                reservaDisponible.setNumeroPlazas(reservaDisponible.getNumeroPlazas()-1);
                reservaDisponibleRepository.save(reservaDisponible);
            }
        }else{
            //Se crea una nueva ReservaDisponible en la BD
            reservaDisponibleRepository.save(new ReservaDisponible(null, reservaOutputDto.getCiudadDestino()
                    , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva(), PLAZAS_BUS));
        }
    }
}

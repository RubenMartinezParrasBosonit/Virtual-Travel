package com.ruben.backweb.reserva.application;

import com.ruben.backweb.reserva.domain.Reserva;
import com.ruben.backweb.reserva.domain.ReservaDisponible;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaDisponibleOutputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import com.ruben.backweb.reserva.infraestructure.repository.ReservaDisponibleRepository;
import com.ruben.backweb.reserva.infraestructure.repository.ReservaRepository;
import com.ruben.backweb.shared.exceptions.BusWithoutEspaceException;
import com.ruben.backweb.shared.kafka.KafkaMessageProducer;
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
    ReservaRepository reservaRepository;

    @Autowired
    ReservaDisponibleRepository reservaDisponibleRepository;

    @Autowired
    KafkaMessageProducer kafkaMessageProducer;


    @Override
    public void hacerReserva(ReservaOutputDto reservaOutputDto){
        Optional<ReservaDisponible> reservaDisponibleOptional = busquedaReservaDisponible(new ReservaDisponibleInputDto(reservaOutputDto.getCiudadDestino(),
                reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva()));

        comprobarReservaDisponible(reservaDisponibleOptional, reservaOutputDto);

        Reserva reserva = new Reserva(null, reservaOutputDto.getCiudadDestino(), reservaOutputDto.getNombre()
                , reservaOutputDto.getApellido(), reservaOutputDto.getTelefono(), reservaOutputDto.getEmail()
                , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva());

        reservaRepository.save(reserva);
    }

    @Override
    public ReservaDisponibleOutputDto buscarDisponibilidad(ReservaDisponibleInputDto reservaDisponibleInputDto){
        Optional<ReservaDisponible> reservaDisponibleOptional = busquedaReservaDisponible(reservaDisponibleInputDto);
        if(!reservaDisponibleOptional.isPresent()){
            return new ReservaDisponibleOutputDto();
        }else{
            return new ReservaDisponibleOutputDto(reservaDisponibleOptional.get());
        }
    }

    @Override
    public ReservaOutputDto hacerReservaKafka(ReservaOutputDto reservaOutputDto) {
        Optional<ReservaDisponible> reservaDisponibleOptional = busquedaReservaDisponible(new ReservaDisponibleInputDto(reservaOutputDto.getCiudadDestino(),
                reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva()));

        comprobarReservaDisponible(reservaDisponibleOptional, reservaOutputDto);

        Reserva reserva = new Reserva(null, reservaOutputDto.getCiudadDestino(), reservaOutputDto.getNombre()
                , reservaOutputDto.getApellido(), reservaOutputDto.getTelefono(), reservaOutputDto.getEmail()
                , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva());

        reservaRepository.save(reserva);
        kafkaMessageProducer.sendMessage(TOPIC,reservaOutputDto);
        return reservaOutputDto;
    }

    @Override
    public void escucharReservaKafka(ReservaOutputDto reservaOutputDto){
        hacerReserva(reservaOutputDto);
    }


    private Optional<ReservaDisponible> busquedaReservaDisponible(ReservaDisponibleInputDto reservaDisponibleInputDto){
        List<ReservaDisponible> listaReservasCiudad = reservaDisponibleRepository.findByCiudadDestino(reservaDisponibleInputDto.getCiudadDestino());
        return listaReservasCiudad.stream().filter(reservaDisponible1 ->
                        reservaDisponible1.getHoraSalida().equals(reservaDisponibleInputDto.getHoraReserva()) &&
                                reservaDisponible1.getFechaSalida().compareTo(reservaDisponibleInputDto.getFechaSalida()) == 0)
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

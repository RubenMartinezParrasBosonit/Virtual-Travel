package com.ruben.backweb.reserva.application;

import com.ruben.backweb.reserva.domain.Reserva;
import com.ruben.backweb.reserva.domain.ReservaDisponible;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.input.ReservaDisponibleRangoInputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaDisponibleOutputDto;
import com.ruben.backweb.reserva.infraestructure.controller.dtos.output.ReservaOutputDto;
import com.ruben.backweb.reserva.infraestructure.repository.ReservaDisponibleRepository;
import com.ruben.backweb.reserva.infraestructure.repository.ReservaRepository;
import com.ruben.backweb.shared.exceptions.BusWithoutEspaceException;
import com.ruben.backweb.shared.kafka.KafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ReservaDisponible reservaDisponible = busquedaReservaDisponible(new ReservaDisponibleInputDto(reservaOutputDto.getCiudadDestino(),
                reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva()));

        comprobarReservaDisponible(reservaDisponible, reservaOutputDto);

        Reserva reserva = new Reserva(null, reservaOutputDto.getCiudadDestino(), reservaOutputDto.getNombre()
                , reservaOutputDto.getApellido(), reservaOutputDto.getTelefono(), reservaOutputDto.getEmail()
                , reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva());

        reservaRepository.save(reserva);
    }

    @Override
    public ReservaDisponibleOutputDto buscarDisponibilidad(ReservaDisponibleInputDto reservaDisponibleInputDto){
        ReservaDisponible reservaDisponible = busquedaReservaDisponible(reservaDisponibleInputDto);
        if(reservaDisponible==null){
            return new ReservaDisponibleOutputDto();
        }else{
            return new ReservaDisponibleOutputDto(reservaDisponible);
        }
    }

    @Override
    public ReservaOutputDto hacerReservaKafka(ReservaOutputDto reservaOutputDto) {
        ReservaDisponible reservaDisponible = busquedaReservaDisponible(new ReservaDisponibleInputDto(reservaOutputDto.getCiudadDestino(),
                reservaOutputDto.getFechaReserva(), reservaOutputDto.getHoraReserva()));

        comprobarReservaDisponible(reservaDisponible, reservaOutputDto);

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

    @Override
    public List<ReservaDisponibleOutputDto> buscarReservasDisponibles (String ciudadDestino, ReservaDisponibleRangoInputDto reservaDisponibleRangoInputDto){
        List<ReservaDisponible> reservaDisponibles = reservaDisponibleRepository.findReservaConRangos(ciudadDestino, reservaDisponibleRangoInputDto.getFechaSuperior()
                , reservaDisponibleRangoInputDto.getFechaInferior(), reservaDisponibleRangoInputDto.getHoraInferior()
                , reservaDisponibleRangoInputDto.getHoraSuperior());
        if(reservaDisponibles!=null){
            List<ReservaDisponibleOutputDto> reservaDisponibleOutputDtos = new ArrayList<>();
            reservaDisponibles.stream().forEach(reservaDisponible -> {
                reservaDisponibleOutputDtos.add(new ReservaDisponibleOutputDto(reservaDisponible));
            });

            return reservaDisponibleOutputDtos;

        }else{
            return null;
        }

    }

    @Override
    public List<ReservaOutputDto> buscarReservas (String ciudadDestino, ReservaDisponibleRangoInputDto reservaDisponibleRangoInputDto){
        List<Reserva> reservas = reservaRepository.findReservaConRangos(ciudadDestino, reservaDisponibleRangoInputDto.getFechaSuperior()
                , reservaDisponibleRangoInputDto.getFechaInferior(), reservaDisponibleRangoInputDto.getHoraInferior(), reservaDisponibleRangoInputDto.getHoraSuperior());

        if(reservas!=null){
            List<ReservaOutputDto> reservaOutputDtos = new ArrayList<>();
            reservas.stream().forEach(reserva -> {
                reservaOutputDtos.add(new ReservaOutputDto(reserva.getCiudadDestino(), reserva.getNombre(), reserva.getApellido()
                        , reserva.getTelefono(), reserva.getEmail(), reserva.getFechaReserva(), reserva.getHoraReserva()));
            });

            return reservaOutputDtos;
        }else{
            return null;
        }
    }



    private ReservaDisponible busquedaReservaDisponible(ReservaDisponibleInputDto reservaDisponibleInputDto){
        return reservaDisponibleRepository.findByCiudadDestinoAndHoraSalidaAndFechaSalida(reservaDisponibleInputDto.getCiudadDestino()
                , reservaDisponibleInputDto.getHoraReserva(), reservaDisponibleInputDto.getFechaSalida());
    }

    private void comprobarReservaDisponible(ReservaDisponible reservaDisponible, ReservaOutputDto reservaOutputDto){
        if(reservaDisponible!=null){
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

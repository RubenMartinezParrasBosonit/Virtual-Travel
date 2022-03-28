package com.ruben.backempresa.correo.application;

import com.ruben.backempresa.correo.domain.Correo;
import com.ruben.backempresa.correo.infraestructure.controller.dtos.input.CorreoInputDto;
import com.ruben.backempresa.correo.infraestructure.repository.CorreoRepository;
import com.ruben.backempresa.correo.infraestructure.utils.EmailUtil;
import com.ruben.backempresa.shared.exceptions.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.*;

@Service
public class CorreoServiceImpl implements CorreoService{

    @Autowired
    CorreoRepository correoRepository;

    @Override
    public void sendMail(CorreoInputDto correoInputDto){
        //Comprobar que el correo está en la base de datos, en caso afirmativo, enviar
        reenviarEmail(correoInputDto);
    }

    @Override
    public void saveMail(CorreoInputDto correoInputDto){
        Correo correo = new Correo(null, correoInputDto.getCiudadDestino(), correoInputDto.getEmail(), correoInputDto.getFechaReserva(), correoInputDto.getHoraReserva());
        enviarEmail(correoInputDto);
        correoRepository.save(correo);
    }

    private void reenviarEmail(CorreoInputDto correoInputDto){
        //Buscamos el correo en la BD para reenviar
        List<Correo> correos = correoRepository.findByEmail(correoInputDto.getEmail());

        if(correos.isEmpty()){
            throw new EmailNotFoundException("No se ha encontrado ninguna reserva con el email: " + correoInputDto.getEmail());
        }

        Correo result = correos.stream().filter(correo ->
                correo.getCiudadDestino().equals(correoInputDto.getCiudadDestino())
                        && correo.getFechaReserva().compareTo(correoInputDto.getFechaReserva()) == 0
                        && correo.getHoraReserva().equals(correoInputDto.getHoraReserva())).findAny()
                .orElseThrow(()->new EmailNotFoundException("No se ha encontrado ninguna reserva con los datos especificados"));


        final String toEmail = result.getEmail(); // can be any email id

        Session session = configurarSesion();

        Date date = result.getFechaReserva();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        EmailUtil.sendEmail(session, toEmail,"Recordatorio viaje Virtual-Travel"
                , "Tiene un viaje hacia: " + result.getCiudadDestino() + "\n" +
                        "El " + day + "/" + month + "/" + year +  " a las " + result.getHoraReserva() + "\n" +
                        "Muchas gracias!");
    }

    private void enviarEmail(CorreoInputDto correoInputDto){
        final String toEmail = correoInputDto.getEmail(); // can be any email id

        Session session = configurarSesion();

        Date date = correoInputDto.getFechaReserva();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        EmailUtil.sendEmail(session, toEmail,"Recordatorio viaje Virtual-Travel"
                , "Tiene un viaje hacia: " + correoInputDto.getCiudadDestino() + "\n" +
                        "El " + day + "/" + month + "/" + year +  " a las " + correoInputDto.getHoraReserva() + "\n" +
                        "Muchas gracias!");
    }

    private Session configurarSesion(){
        final String fromEmail = "virtual.travel.exercise@gmail.com"; //requires valid gmail id
        final String password = "bosonit1"; // correct password for gmail id

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        return Session.getInstance(props, auth);
    }

}

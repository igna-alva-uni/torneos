package cl.duoc.notificaciones.service;

import org.springframework.stereotype.Service;
import cl.duoc.notificaciones.dto.NotificacionDTO;

@Service
public class NotificacionService {

    public String enviar(NotificacionDTO dto) {
        // simulamos envío
        System.out.println("Notificación enviada a: " + dto.getUsuario());
        System.out.println("Mensaje: " + dto.getMensaje());

        return "Notificación procesada correctamente";
    }
}



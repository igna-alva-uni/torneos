package cl.duoc.notificaciones.dto;

public class NotificacionDTO {

    private String mensaje;
    private String usuario;

    public NotificacionDTO() {}

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}



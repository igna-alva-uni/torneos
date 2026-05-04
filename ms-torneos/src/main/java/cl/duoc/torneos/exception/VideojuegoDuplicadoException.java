package cl.duoc.torneos.exception;

public class VideojuegoDuplicadoException extends RuntimeException {
    public VideojuegoDuplicadoException(String nom_torneo, String videojuego) {
        super("Ya hay registro de un torneo de nombre: "+nom_torneo+ " con el videojuego: "+videojuego);
    }

}

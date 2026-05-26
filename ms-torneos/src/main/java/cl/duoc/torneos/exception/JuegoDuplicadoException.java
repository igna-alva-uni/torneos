package cl.duoc.torneos.exception;

public class JuegoDuplicadoException extends RuntimeException {
    public JuegoDuplicadoException(String id, String videojuego) {
        super("Ya existe un torneo con el ID: "+id+"con el videojuego: "+videojuego);
    }

}

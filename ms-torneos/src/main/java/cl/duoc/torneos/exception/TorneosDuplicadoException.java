package cl.duoc.torneos.exception;

public class TorneosDuplicadoException extends RuntimeException {
    public TorneosDuplicadoException(String nombre, Integer idJuego) {
        super("Ya existe un torneo con el nombre: "+nombre);
    }
}

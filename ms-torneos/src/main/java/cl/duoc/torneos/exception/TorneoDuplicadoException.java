package cl.duoc.torneos.exception;

public class TorneoDuplicadoException extends RuntimeException {
    public TorneoDuplicadoException(String nombre, Integer idJuego) {
        super("Ya existe un torneo con el nombre: "+nombre);
    }
}

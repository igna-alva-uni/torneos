package cl.duoc.torneos.exception;

public class TorneoNotFoundException extends RuntimeException {
    public TorneoNotFoundException(Integer id) {
        super("No existe el torneo " + id);
    }
    public TorneoNotFoundException(String videojuego) {
        super("No existe el torneo con el videojuego: " + videojuego);
    }
}

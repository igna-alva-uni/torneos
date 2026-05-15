package cl.duoc.torneos.exception;

public class TorneoNotFoundException extends RuntimeException {
    public TorneoNotFoundException(Integer id) {
        super("No existe el torneo con id: " + id);
    }
    public TorneoNotFoundException() {
        super("No hay torneos registrados");
    }
    public TorneoNotFoundException porJuego(Integer idJuego) {
        return new TorneoNotFoundException(
                "No existe torneos asociados al juego con id: "+idJuego
        );
    }
    private TorneoNotFoundException(String message) {
        super(message);
    }

}

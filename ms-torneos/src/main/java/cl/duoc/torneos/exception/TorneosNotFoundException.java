package cl.duoc.torneos.exception;

public class TorneosNotFoundException extends RuntimeException {
    public TorneosNotFoundException(Integer id) {
        super("No existe el torneo con id: " + id);
    }
    public TorneosNotFoundException() {
        super("No hay torneos registrados");
    }
    public TorneosNotFoundException porJuego(Integer idJuego) {
        return new TorneosNotFoundException(
                "No existe torneos asociados al juego con id: "+idJuego
        );
    }
    private TorneosNotFoundException(String message) {
        super(message);
    }

}

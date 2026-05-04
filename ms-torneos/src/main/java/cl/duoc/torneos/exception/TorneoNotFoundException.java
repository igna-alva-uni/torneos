package cl.duoc.torneos.exception;

import cl.duoc.torneos.model.Formato;

public class TorneoNotFoundException extends RuntimeException {
    public TorneoNotFoundException(Long id) {
        super("No se encontró ningun torneo con el ID: "+id);
    }
    public TorneoNotFoundException(String videojuego) {
        super("No se encontro torneo con el videojuego: "+videojuego);
    }
}

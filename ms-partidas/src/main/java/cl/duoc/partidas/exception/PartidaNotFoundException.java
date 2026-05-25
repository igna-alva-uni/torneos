package cl.duoc.partidas.exception;

public class PartidaNotFoundException extends RuntimeException {
    public PartidaNotFoundException(Integer id) {
        super("No existe la partida con el id " + id);
    }
}

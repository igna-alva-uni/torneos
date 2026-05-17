package cl.duoc.juegos.exception;

public class JuegoNotFoundException extends RuntimeException {
    public JuegoNotFoundException(Integer id) {
        super("No existe el videojuego con el ID: "+id);
    }
}

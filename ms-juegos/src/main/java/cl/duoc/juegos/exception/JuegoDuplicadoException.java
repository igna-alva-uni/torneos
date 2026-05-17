package cl.duoc.juegos.exception;

public class JuegoDuplicadoException extends RuntimeException {
    public JuegoDuplicadoException(String nombre) {
        super("Ya existe un videojuego con el nombre: "+nombre);
    }
}

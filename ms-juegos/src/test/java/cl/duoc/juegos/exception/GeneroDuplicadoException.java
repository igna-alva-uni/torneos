package cl.duoc.juegos.exception;

public class GeneroDuplicadoException extends RuntimeException{
    public GeneroDuplicadoException(String genero, String nombre){
        super("Ya existe un videojuego registrado con el genero: "+genero+", con el nombre de: "+nombre);
    }
}

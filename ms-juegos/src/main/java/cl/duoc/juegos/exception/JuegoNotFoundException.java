package cl.duoc.juegos.exception;

public class JuegoNotFoundException extends RuntimeException{
    public JuegoNotFoundException(int id){
        super("No se encontró ningun juego con el ID: "+id);
    }
    public JuegoNotFoundException(String genero){
        super("No se encontró ningun videojuego con el genero de: "+genero);
    }
}

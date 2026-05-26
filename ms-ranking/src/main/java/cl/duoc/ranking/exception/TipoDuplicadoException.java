package cl.duoc.ranking.exception;

public class TipoDuplicadoException extends RuntimeException {
    public TipoDuplicadoException(String tipoRanking, String nombre){
        super("Ya existe un tipo de ranking registrado con : "+tipoRanking+", con el nombre de: "+nombre);
    }
}

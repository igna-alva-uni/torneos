package cl.duoc.ranking.exception;

public class RankingNotFoundException extends RuntimeException {
        public RankingNotFoundException(int id){
            super("No se encontró ningun ranking con el ID: "+ id );
    }
    public RankingNotFoundException(String tipoRanking){
        super("No se encontró ningun ranking con el tipo de: "+tipoRanking);
    }
}

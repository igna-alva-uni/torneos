package cl.duoc.ranking.exception;

public class RankingNotFoundException extends RuntimeException {
        public RankingNotFoundException(int id){
            super("Ranking no encontrado con el ID: "+ id );
    }
    public RankingNotFoundException(String tipoRanking){
        super("Ranking no encontrado con el tipo: "+tipoRanking);
    }
}

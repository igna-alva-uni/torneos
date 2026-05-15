package cl.duoc.ranking.mapper;

import org.mapstruct.Mapper;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.model.Ranking;
import cl.duoc.ranking.model.TipoRanking;

@Mapper(componentModel = "spring")
public interface RankingMapper {

    @Mapping(source = "idRanking", target = "id")
    @Mapping(source = "tipoRanking.nombreTipoRanking", target = "tipoRanking")
    @Mapping(target = "registroRanking", ignore = true) 
    @Mapping(target = "rankings", ignore = true)        
    RankingResponse toResponse(Ranking ranking);

    List<RankingResponse> toResponseList(List<Ranking> rankings);

    @Mapping(target = "idRanking", ignore = true)
    @Mapping(target = "registros", ignore = true)
    @Mapping(target = "idJuego", ignore = true) 
    @Mapping(target = "tipoRanking", source = "tipoRanking", qualifiedByName = "stringToTipoRanking")
    Ranking toModel(RankingRequest request);

    @Mapping(target = "idRanking", ignore = true)
    @Mapping(target = "tipoRanking", ignore = true)
    @Mapping(target = "registros", ignore = true)
    @Mapping(target = "idJuego", ignore = true)
    Ranking updateModelFromDto(RankingRequest request, @MappingTarget Ranking entity);

    // ESTE ES EL MÉTODO QUE SOLUCIONA EL ERROR
    @Named("stringToTipoRanking")
    default TipoRanking map(String value) {
        if (value == null) return null;
        TipoRanking tipo = new TipoRanking();
        tipo.setNombreTipoRanking(value);
        return tipo;
    }
}
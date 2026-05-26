package cl.duoc.ranking.mapper;

import cl.duoc.ranking.dto.ranking.RankingRequest;
import cl.duoc.ranking.dto.ranking.RankingResponse;
import cl.duoc.ranking.model.Ranking;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TipoRankingMapper.class, RegistroRankingMapper.class})
public interface RankingMapper {

    RankingResponse toResponse(Ranking entity);


    @Mapping(target = "idRanking", ignore = true)
    @Mapping(target = "tipoRanking.idTipoRanking", source = "idTipoRanking") 
    Ranking toEntity(RankingRequest request);


    @AfterMapping
    default void vincularRegistrosPadre(@MappingTarget Ranking ranking) {
        if (ranking.getRegistros() != null) {
            ranking.getRegistros().forEach(registro -> registro.setRanking(ranking));
        }
    }
}
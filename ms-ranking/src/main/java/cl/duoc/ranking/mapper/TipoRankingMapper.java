package cl.duoc.ranking.mapper;

import cl.duoc.ranking.dto.tipoRanking.TipoRankingRequest;
import cl.duoc.ranking.dto.tipoRanking.TipoRankingResponse;
import cl.duoc.ranking.model.TipoRanking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoRankingMapper {

    TipoRankingResponse toResponse(TipoRanking entity);

    @Mapping(target = "idTipoRanking", ignore = true)
    @Mapping(target = "rankings", ignore = true)
    TipoRanking toEntity(TipoRankingRequest request);
}
package cl.duoc.ranking.mapper;

import org.mapstruct.Mapper;
import java.util.List;
import org.mapstruct.Mapping;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.model.Ranking;

@Mapper(componentModel = "spring")
public interface RankingMapper {

    @Mapping(source = "idRanking", target = "id")
    @Mapping(source = "tipoRanking.nombreTipoRanking", target = "tipoRanking")
    RankingResponse toResponse(Ranking ranking);

    List<RankingResponse> toResponseList(List<Ranking> rankings);

    @Mapping(target = "idRanking", ignore = true)
    @Mapping(target = "tipoRanking", ignore = true)
    @Mapping(target = "registros", ignore = true)
    Ranking toModel(RankingRequest request);
}
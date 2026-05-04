package cl.duoc.ranking.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.duoc.ranking.dto.RankingRequest;
import cl.duoc.ranking.dto.RankingResponse;
import cl.duoc.ranking.model.Ranking;

@Mapper(componentModel = "spring")
public interface RankingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "regitroRanking", ignore = true)
    @Mapping(target = "tipoRanking", ignore = true)
    @Mapping(target = "rankings", ignore = true)


    Ranking toModel( RankingRequest request );
    RankingResponse toResponse( Ranking ranking );
    List<RankingResponse> toResponseList( List<Ranking> ranking );

}

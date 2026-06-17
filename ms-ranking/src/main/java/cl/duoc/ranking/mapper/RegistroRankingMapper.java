package cl.duoc.ranking.mapper;

import cl.duoc.ranking.dto.registroRanking.RegistroRankingRequest;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingResponse;
import cl.duoc.ranking.model.RegistroRanking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegistroRankingMapper {

    RegistroRankingResponse toResponse(RegistroRanking entity);

    @Mapping(target = "idRegistroRanking", ignore = true)
    @Mapping(target = "ranking", ignore = true) // Se maneja en el padre (Ranking)
    RegistroRanking toEntity(RegistroRankingRequest request);
}
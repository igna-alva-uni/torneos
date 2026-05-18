package cl.duoc.ranking.service;

import cl.duoc.ranking.client.EstadisticaClient;
import cl.duoc.ranking.client.EstadisticaEquipoResponse;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingRequest;
import cl.duoc.ranking.dto.registroRanking.RegistroRankingResponse;
import cl.duoc.ranking.mapper.RegistroRankingMapper;
import cl.duoc.ranking.model.RegistroRanking;
import cl.duoc.ranking.repository.RegistroRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroRankingService {

    private final EstadisticaClient estadisticaClient; // Nuestro cliente Feign para llamar al ms-estadisticas
    private final RegistroRankingRepository repository;
    private final RegistroRankingMapper mapper;

    @Transactional(readOnly = true)
    public List<RegistroRankingResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

   @Transactional(readOnly = true)
public RegistroRankingResponse findById(Integer id) {
    RegistroRanking registro = repository.findByIdRegistroRanking(id)
            .orElseThrow(() -> new RuntimeException("Registro de ranking no encontrado con ID: " + id));
    
    RegistroRankingResponse response = mapper.toResponse(registro);

    try {
        EstadisticaEquipoResponse stats = estadisticaClient.getTeamStatsById(registro.getIdEquipo());
        response.setEstadisticas(stats);
    } catch (Exception e) {
        System.out.println("No se pudieron obtener las estadísticas para este equipo.");
    }
    return response;
    }

    @Transactional
    public RegistroRankingResponse update(Integer id, RegistroRankingRequest request) {
        RegistroRanking existingRegistro = repository.findByIdRegistroRanking(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));
        
        existingRegistro.setIdEquipo(request.getIdEquipo());
        existingRegistro.setPuntos(request.getPuntos());
        
        RegistroRanking updatedRegistro = repository.save(existingRegistro);
        return mapper.toResponse(updatedRegistro);
    }

    @Transactional
    public void delete(Integer id) {
        RegistroRanking registro = repository.findByIdRegistroRanking(id)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: El registro no existe."));
        repository.delete(registro);
    }


}
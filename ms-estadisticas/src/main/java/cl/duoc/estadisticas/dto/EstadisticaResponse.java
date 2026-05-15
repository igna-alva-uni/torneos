package cl.duoc.estadisticas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadisticaResponse {
    private Integer id;             
    private Integer idReferencia;    
    private String tipo;            

   
    private Integer victorias;      
    private Integer derrotas;       
    

    private Double porcentajeExito; 
    private String tiempoTotal;     
    private Long segundos;         
}
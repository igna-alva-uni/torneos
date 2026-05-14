package cl.duoc.equipos.dtos.miembro;

import lombok.Data;

@Data
public class MiembroEquipoResponse {
    private Long id;
    private Long idUsuario;
    private Long idEquipo;
    private String nombreEquipo;
    private Long idRolEquipo;
    private String nombreRolEquipo;
}
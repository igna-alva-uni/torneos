package cl.duoc.torneos.dto;

import cl.duoc.torneos.model.Formato;
import cl.duoc.torneos.model.Premio;

import lombok.Data;

@Data
public class TorneosResponse {
    private Integer id;
    private String nom_torneo;
    private String videojuego;
    private Formato formato;
    private Premio premio;
}

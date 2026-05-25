package cl.duoc.partidas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resultados_partidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultado_partida")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_partida", nullable = false, unique = true)
    private Partida partida;

    @Column(name = "id_equipo_ganador")
    private Integer idEquipoGanador;

    @Column(nullable = false)
    private String puntaje;
}

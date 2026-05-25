package cl.duoc.partidas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partidas", schema = "partidas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida")
    private Integer id;

    @Column(name = "id_torneo")
    private Integer torneoId;

    @Column(nullable = false)
    private String ronda;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_partida")
    private EstadoPartida estado;

    @OneToOne(mappedBy = "partida")
    private ResultadoPartida resultado;
}

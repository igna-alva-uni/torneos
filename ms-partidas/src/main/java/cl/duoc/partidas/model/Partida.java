package cl.duoc.partidas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "partidas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partidas")
    private Integer id;

    @Column(name = "id_torneos")
    private Integer torneos;

    @Column(nullable = false)
    private String ronda;

    @Enumerated(EnumType.STRING)
    @Column(name = "")
    private EstadoPartida estado;
}

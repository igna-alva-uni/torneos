package cl.duoc.partidas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jugadores_partida", schema = "partidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JugadorPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador_partida")
    private Integer id;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_partida", nullable = false)
    private Partida partida;

    @Column(name = "id_equipo", nullable = false)
    private Integer idEquipo;
}

package cl.duoc.estadisticas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estadisticas_jugadores", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id_usuario")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadistica_jugador")
    private Integer idEstadisticaJugador;

    @Column(name = "id_usuario", nullable = false, unique = true)
    private Integer idUsuario;

    @Builder.Default
    @Column(name = "victorias_jugador")
    private Integer victoriasJugador = 0;

    @Builder.Default
    @Column(name = "derrotas_jugador")
    private Integer derrotasJugador = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadisticaJugador)) return false;
        EstadisticaJugador that = (EstadisticaJugador) o;
        return idEstadisticaJugador != null && idEstadisticaJugador.equals(that.idEstadisticaJugador);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

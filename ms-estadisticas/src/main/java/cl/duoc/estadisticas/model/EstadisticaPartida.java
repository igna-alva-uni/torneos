package cl.duoc.estadisticas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Duration;

@Entity
@Table(name = "estadisticas_partidas", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id_partida")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaPartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadistica_partida")
    private Integer idEstadisticaPartida;

    @Column(name = "id_partida", nullable = false, unique = true)
    private Integer idPartida;

    @Column(name = "duracion", nullable = false)
    private Duration duracion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadisticaPartida)) return false;
        EstadisticaPartida that = (EstadisticaPartida) o;
        return idEstadisticaPartida != null && idEstadisticaPartida.equals(that.idEstadisticaPartida);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
package cl.duoc.estadisticas.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estadisticas_equipos", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id_equipo")
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadistica_equipo")
    private Integer idEstadisticaEquipo;

    @Column(name = "id_equipo", nullable = false, unique = true)
    private Integer idEquipo;

    @Builder.Default
    @Column(name = "victorias_equipo")
    private Integer victoriasEquipo = 0;

    @Builder.Default
    @Column(name = "derrotas_equipo")
    private Integer derrotasEquipo = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EstadisticaEquipo)) return false;
        EstadisticaEquipo that = (EstadisticaEquipo) o;
        return idEstadisticaEquipo != null && idEstadisticaEquipo.equals(that.idEstadisticaEquipo);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

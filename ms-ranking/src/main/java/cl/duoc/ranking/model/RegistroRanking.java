package cl.duoc.ranking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "registros_ranking", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_ranking", "id_equipo"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_ranking")
    private Integer idRegistroRanking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ranking", nullable = false)
    private Ranking ranking;

    @Column(name = "id_equipo", nullable = false)
    private Integer idEquipo;

    @Builder.Default
    @Column(name = "puntos", nullable = false)
    private Integer puntos = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroRanking)) return false;
        RegistroRanking that = (RegistroRanking) o;
        return idRegistroRanking != null && idRegistroRanking.equals(that.idRegistroRanking);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
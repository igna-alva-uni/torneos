package cl.duoc.ranking.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "tipos_ranking", uniqueConstraints = {
    @UniqueConstraint(columnNames = "nombre_tipo_ranking")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ranking")
    private Integer idTipoRanking;

    @Column(name = "nombre_tipo_ranking", nullable = false, length = 50)
    private String nombreTipoRanking;

    @OneToMany(mappedBy = "tipoRanking")
    private List<Ranking> rankings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoRanking)) return false;
        TipoRanking that = (TipoRanking) o;
        return idTipoRanking != null && idTipoRanking.equals(that.idTipoRanking);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
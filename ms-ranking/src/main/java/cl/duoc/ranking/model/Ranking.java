package cl.duoc.ranking.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "rankings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_juego", "id_tipo_ranking"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ranking")
    private Integer idRanking;

    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_ranking", nullable = false)
    private TipoRanking tipoRanking;

    @OneToMany(mappedBy = "ranking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroRanking> registros;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ranking)) return false;
        Ranking ranking = (Ranking) o;
        return idRanking != null && idRanking.equals(ranking.idRanking);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
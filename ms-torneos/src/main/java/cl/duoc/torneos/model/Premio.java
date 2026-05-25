package cl.duoc.torneos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Premio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_premio")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_torneo", nullable = false)
    private Torneos torneos;

    @Column(nullable = false)
    private Integer pocision;

    @Column(nullable = false)
    private String recompensa;

}

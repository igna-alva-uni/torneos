package cl.duoc.torneos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "premios", schema = "torneos")
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

    @Column(name = "posicion", nullable = false)
    private Integer posicion;

    @Column(nullable = false)
    private String recompensa;

}

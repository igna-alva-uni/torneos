package cl.duoc.torneos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Torneos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_torneo")
    private Integer id;

    @Column(name = "nombre_torneo", nullable = false)
    private String nombre;

    @Column(name = "id_juego", nullable = false)
    private Integer idJuego;

    @ManyToOne
    @JoinColumn(name = "id_formato", nullable = false)
    private Formato formato;

    @Column(name = "fecha_inicio")
    private LocalDate fecha_inicio;

    @Column(name = "fecha_termino")
    private LocalDate fecha_final;

    @OneToMany(mappedBy = "torneos", cascade = CascadeType.ALL)
    private List<Premio> premios;
}

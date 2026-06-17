package cl.duoc.juegos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "juegos", schema = "juegos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Juegos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_juego")
    private Integer id;

    @Column(name = "nombre_juego", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "plataformas_juegos",
            joinColumns = @JoinColumn(name = "id_juego"),
            inverseJoinColumns = @JoinColumn(name = "id_plataforma")
    )
    private Set<Plataformas> plataformas;
}

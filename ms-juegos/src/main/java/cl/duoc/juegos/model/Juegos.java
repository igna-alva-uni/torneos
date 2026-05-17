package cl.duoc.juegos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
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

    @Column(name = "nom_juegos", nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    private String descripcion;

    @ManyToMany
    @JoinTable(name = "plataformas_juegos",
            joinColumns = @JoinColumn(name = "id_juego"),
            inverseJoinColumns = @JoinColumn(name = "id_platadorma")
    )
    private Set<Plataforma> plataformas;
}

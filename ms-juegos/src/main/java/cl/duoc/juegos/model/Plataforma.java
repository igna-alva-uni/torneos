package cl.duoc.juegos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "plataforma")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plataforma")
    private Integer id;

    @Column(name = "nom_plataforma", nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "plataformas")
    private Set<Juegos> juegos;
}

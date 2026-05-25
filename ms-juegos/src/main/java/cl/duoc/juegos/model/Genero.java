package cl.duoc.juegos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "genero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Integer id;

    @Column(name = "nombre_genero", nullable = false, unique = true)
    private String nombre;
}

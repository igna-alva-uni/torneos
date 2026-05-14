package cl.duoc.equipos.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipos")
public class Equipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    private Long id;

    @Column(name = "nombre_equipo", nullable = false, unique = true, length = 100)
    private String nombreEquipo;

    @Column(name = "fundado_el", insertable = false, updatable = false)
    private LocalDate fundadoEl;
}
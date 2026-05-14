package cl.duoc.equipos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "roles_equipo")
public class RolEquipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol_equipo")
    private Long id;

    @Column(name = "nombre_rol_equipo", nullable = false, unique = true, length = 50)
    private String nombreRolEquipo;
}
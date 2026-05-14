package cl.duoc.equipos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "miembros_equipos")
public class MiembroEquipo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_miembro_equipo")
    private Long id;

    @Column(name = "id_usuario", nullable = false, unique = true)
    private Long idUsuario;
    
    @ManyToOne
    @JoinColumn(name = "id_equipo", referencedColumnName = "id_equipo", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "id_rol_equipo", referencedColumnName = "id_rol_equipo", nullable = false)
    private RolEquipo rolEquipo;
}
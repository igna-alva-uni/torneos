package cl.duoc.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipos_notificacion", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre_tipo_notificacion")})
public class TipoNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_notificacion", nullable = false)
    private Integer idTipoNotificacion;

    @Column(name = "nombre_tipo_notificacion", nullable = false, length = 50)
    private String nombreTipoNotificacion;

    @OneToMany(mappedBy = "tipoNotificacion", cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;
}



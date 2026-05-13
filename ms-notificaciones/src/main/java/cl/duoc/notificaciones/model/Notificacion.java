package cl.duoc.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion", nullable = false)
    private Integer idNotificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_notificacion", referencedColumnName = "id_tipo_notificacion", nullable = false)
    private TipoNotificacion tipoNotificacion;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @OneToMany(mappedBy = "notificacion", cascade = CascadeType.ALL)
    private List<NotificacionUsuario> notificacionesUsuarios;

}
   
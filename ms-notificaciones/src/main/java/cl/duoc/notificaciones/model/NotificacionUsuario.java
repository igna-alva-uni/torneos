package cl.duoc.notificaciones.model;

public class NotificacionUsuario {
    package cl.duoc.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notificaciones_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificacionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion_usuario", nullable = false)
    private Integer idNotificacionUsuario;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_notificacion", nullable = false)
    private Notificacion notificacion;

    @Column(name = "leida")
    @Builder.Default
    private Boolean leida = false;
}

}

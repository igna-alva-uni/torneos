package cl.duoc.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notificaciones_usuario")
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
    private Boolean leida;
}

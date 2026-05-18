package cl.duoc.autenticaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "auth_usuarios")
public class AuthUser {
    
    @Id
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "hash_contrasenia", nullable = false, length = 255)
    private String hashContrasenia;

    @Column(name = "bloqueada")
    private Boolean bloqueada = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_usuarios",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Role> roles;
}
package cl.duoc.usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "paises")
public class Pais {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private Long id;

    @Column(name = "nombre_pais", nullable = false, length = 100)
    private String nombrePais;

    @Column(name = "codigo_pais", nullable = false, unique = true, length = 3)
    private String codigoPais;
}
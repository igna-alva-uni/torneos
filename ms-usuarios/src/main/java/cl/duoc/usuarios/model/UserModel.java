package cl.duoc.usuarios.model;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel {
    private int id;
    private String username;
    private String email;
    private LocalDate createdOn;
}

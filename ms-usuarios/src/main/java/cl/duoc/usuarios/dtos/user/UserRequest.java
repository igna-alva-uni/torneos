package cl.duoc.usuarios.dtos.user;

import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    @Size(min = 5, max = 100)
    @NotBlank
    private String username;

    @Email
    @NotBlank
    @Size(max = 150)
    private String email;
    
    @PastOrPresent
    private LocalDate creadoEl;

}

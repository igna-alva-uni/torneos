package cl.duoc.usuarios.dtos.user;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class UserRequest {

    @Size(min = 2, max = 100)
    @NotBlank
    private String username;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;
    
    @PastOrPresent
    private LocalDate createdOn;
}

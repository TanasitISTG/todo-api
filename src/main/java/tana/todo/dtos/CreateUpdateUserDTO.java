package tana.todo.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateUserDTO {
    @NotNull @NotEmpty @NotBlank @Size(min = 1, max = 20)
    private String username;
    @NotNull @NotEmpty @NotBlank @Size(min = 1, max = 50)
    @Email(message = "Email should be valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @NotNull @NotEmpty @NotBlank @Size(min = 1, max = 100)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).+$",
             message = "must be 8-14 characters long, at least 1 of uppercase, lowercase, number and special characters")
    private String password;
}

package tana.todo.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tana.todo.enums.Status;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateTaskDTO {
    @NotNull @NotBlank @NotEmpty @Size(min = 1, max = 100)
    private String title;
    private String description;
    private Status status;
    @Future
    private ZonedDateTime due_datetime;
}

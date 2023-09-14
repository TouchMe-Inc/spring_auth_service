package by.touchme.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseLoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;
}

package by.touchme.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshDto
{
    @NotNull
    private String refreshToken;
}

package by.touchme.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDto {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}

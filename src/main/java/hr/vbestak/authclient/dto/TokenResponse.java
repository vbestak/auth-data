package hr.vbestak.authclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String tokenType;
    private String accessToken;
    private String refreshToken;
}

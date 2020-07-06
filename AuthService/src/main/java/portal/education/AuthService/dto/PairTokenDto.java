package portal.education.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PairTokenDto {
    private String accessToken;
    private String refreshToken;
}

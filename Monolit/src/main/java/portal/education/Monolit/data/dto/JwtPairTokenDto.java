package portal.education.Monolit.data.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JwtPairTokenDto {

    @NotNull
    private String accessToken;

    @NotNull
    private String refreshToken;

    public JwtPairTokenDto(@NotNull String accessToken, @NotNull String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}

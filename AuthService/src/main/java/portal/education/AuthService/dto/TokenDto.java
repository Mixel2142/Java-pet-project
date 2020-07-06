package portal.education.AuthService.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TokenDto {

    @NotNull
    @NotEmpty
    private String refreshToken;

}

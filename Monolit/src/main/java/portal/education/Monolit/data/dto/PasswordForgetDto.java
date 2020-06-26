package portal.education.Monolit.data.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PasswordForgetDto {

    @NotNull String nickname;
    @Hidden
    @NotNull String secret;
    @URL String redirectOk;
    @URL String redirectError;
}

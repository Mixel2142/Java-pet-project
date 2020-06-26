package portal.education.Monolit.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PasswordDto {
    @NotNull
    String oldPassword;

    @NotNull
    @Size(min = 1,max = 26)
    String newPassword;
}

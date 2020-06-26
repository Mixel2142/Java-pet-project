package portal.education.Monolit.data.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 1, max = 26)
    String nickname;

    @NotNull
    @Size(min = 1, max = 26)
    String password;

    @Hidden
    String role;

    public UserDto(UserDto dto) {
        this.nickname = dto.getNickname();
        this.password = dto.getPassword();
    }
}

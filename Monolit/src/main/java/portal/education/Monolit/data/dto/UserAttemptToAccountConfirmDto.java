package portal.education.Monolit.data.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class UserAttemptToAccountConfirmDto {

    @Parameter(description = "Ник пользователя")
    String nickname;

    @Parameter(description = "Число оставшихся попыток пользователя")
    Short attempts;

    @Parameter(description = "Время доступное для следующей попытки")
    Date nextAttemptTime;
}

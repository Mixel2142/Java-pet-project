package portal.education.Monolit.data.dto;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@NoArgsConstructor
public class ViewDto {

    @NotNull
    private UUID articleId;

    @Hidden
    private String nickName;

    @NotNull
    private Long timeView;
}

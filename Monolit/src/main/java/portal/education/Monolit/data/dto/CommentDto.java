package portal.education.Monolit.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
@AllArgsConstructor
public class CommentDto {

    @NotNull
    private UUID articleId;

    private Long toCommentId;

    @NotNull
    private String text;
}

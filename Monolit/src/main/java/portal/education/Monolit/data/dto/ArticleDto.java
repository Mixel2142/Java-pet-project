package portal.education.Monolit.data.dto;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    @Hidden
    private UUID authorId;

    @Hidden
    private UUID articleId;

    @NotNull
    private String title;

    @NotNull
    private String body;

    @NotNull
    private String status;

    @NotNull
    private String category;

    List<String> tags;
}

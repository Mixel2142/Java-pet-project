package portal.education.Monolit.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ArticleStatusDto {
    @NotNull
    Integer indexPage = 0;
    @NotNull
    Integer sizePage = 0;

    String status;
}

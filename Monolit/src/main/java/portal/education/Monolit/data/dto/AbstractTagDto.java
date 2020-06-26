package portal.education.Monolit.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.person.User;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@JsonView({JsonViews.Short.class,JsonViews.Public.class,JsonViews.Internal.class,JsonViews.Author.class})
public class AbstractTagDto {

    @NotNull
    String name;

    @Hidden
    User author;


}

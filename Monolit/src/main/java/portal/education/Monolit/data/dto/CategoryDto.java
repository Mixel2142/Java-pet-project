package portal.education.Monolit.data.dto;

import portal.education.Monolit.data.model.person.User;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CategoryDto {

    @NotNull
    String name;

    @Hidden
    User author;


}

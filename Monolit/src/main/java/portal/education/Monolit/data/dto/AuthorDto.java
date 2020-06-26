package portal.education.Monolit.data.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthorDto extends UserDto {

    @NotNull
    private Long rating;

    public AuthorDto(UserDto userDto) {
        super(userDto);

        this.rating = rating;
    }
}

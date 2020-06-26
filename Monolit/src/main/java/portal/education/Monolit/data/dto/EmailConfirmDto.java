package portal.education.Monolit.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.Email;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmDto {

    @Email String email;
    @URL String redirectOk;
    @URL String redirectError;
}

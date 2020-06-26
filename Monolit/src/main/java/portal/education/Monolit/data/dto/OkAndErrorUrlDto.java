package portal.education.Monolit.data.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class OkAndErrorUrlDto {

    protected @URL String redirectOk;
    protected @URL String redirectError;

    @Hidden
    private HttpRequestUrlsDto request;
}

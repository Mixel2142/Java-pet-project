package portal.education.Monolit.data.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.Expose;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.person.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@JsonView(JsonViews.Public.class)
public class AccountConfirmDto extends OkAndErrorUrlDto {

    @Hidden
    @Expose
    User from;

    @Parameter(description = "Название почты или id ВК (Зависит от того куда отправлять)")
    String to;

    String typeNotification;

    String typeMailSender;

    public AccountConfirmDto(@URL String redirectOk, @URL String redirectError, HttpRequestUrlsDto request, User from, String to, String typeNotification, String typeMailSender) {
        super(redirectOk, redirectError, request);
        this.from = from;
        this.to = to;
        this.typeNotification = typeNotification;
        this.typeMailSender = typeMailSender;
    }
}

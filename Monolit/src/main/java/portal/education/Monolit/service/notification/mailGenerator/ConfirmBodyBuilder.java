package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.dto.OkAndErrorUrlDto;
import portal.education.Monolit.data.model.person.User;

public interface ConfirmBodyBuilder {

    String build(User user, String token, OkAndErrorUrlDto dto, String greeting, String infoMsg, String url);
}

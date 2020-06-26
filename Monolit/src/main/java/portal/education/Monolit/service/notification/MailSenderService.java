package portal.education.Monolit.service.notification;

import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;

public interface MailSenderService {

    ItemsDto<Object> send(MailInfo mailInfo);

}

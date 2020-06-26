package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.model.notification.AccountConfirmation;
import org.springframework.mail.SimpleMailMessage;

public interface EmailConstructHelper {

    SimpleMailMessage constructEmail(String subject, String body, AccountConfirmation confirmation);
}

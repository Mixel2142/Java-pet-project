package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.model.notification.AccountConfirmation;
import portal.education.Monolit.service.notification.controller.ConfirmNotification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailConstructHelperImpl extends ConfirmNotification implements EmailConstructHelper {


    @Override
    public SimpleMailMessage constructEmail(String subject, String body, AccountConfirmation confirmation) {

        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(confirmation.getAccountIdentification());
        email.setFrom(SUPPORT_EMAIL);
        return email;
    }

}

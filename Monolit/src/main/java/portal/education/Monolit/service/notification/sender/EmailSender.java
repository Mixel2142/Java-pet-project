package portal.education.Monolit.service.notification.sender;

import portal.education.Monolit.service.JsonObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements Sender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    JsonObjectConverter jsonObjectConverter;

    @Override
    public void send( String message) {
        mailSender.send(jsonObjectConverter.jsonToObject(message, SimpleMailMessage.class));
    }


    @Override
    public String getMyType() {
        return TypeMailSender.EMAIL;
    }
}

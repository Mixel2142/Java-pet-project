package portal.education.EmailSender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

//    @Autowired
//    JsonObjectConverter jsonObjectConverter;

    public void send(String message) {
//        mailSender.send(jsonObjectConverter.jsonToObject(message, SimpleMailMessage.class));
    }

}

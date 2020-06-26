package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.notification.MailSenderServiceImpl;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;

public interface MailGenerator {
    String generate(MailInfo mailInfo, User user);

    String getMyType();

    @Autowired// setter injection (from pattern Registry)
    /*
    У бина mailSender будут запускаться:
    @PostConstruct
    @Scheduled
    @EventListener
    @Autowired
     */
    default void registerMySelf(MailSenderServiceImpl mailSender) {
        mailSender.register(getMyType(), this);
    }
}

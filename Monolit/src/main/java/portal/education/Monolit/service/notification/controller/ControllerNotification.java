package portal.education.Monolit.service.notification.controller;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.notification.MailSenderServiceImpl;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;

public interface ControllerNotification {
    public boolean isAvailable(MailInfo mailInfo, User user);// внутри него будет идти public void decrementAttempts(User user);

    public Object info(User user);

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

package portal.education.Monolit.service.notification.sender;

import portal.education.Monolit.service.notification.MailSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public interface Sender {
    void send(String message);

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

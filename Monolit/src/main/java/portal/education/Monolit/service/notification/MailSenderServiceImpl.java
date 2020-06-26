package portal.education.Monolit.service.notification;

import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.notification.controller.ControllerNotification;
import portal.education.Monolit.service.notification.mailGenerator.MailGenerator;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import portal.education.Monolit.service.notification.sender.Sender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private Map<String, MailGenerator> generatorMap = new HashMap<>();
    private Map<String, Sender> senderMap = new HashMap<>();
    private Map<String, ControllerNotification> ctrlNotificationMap = new HashMap<>();


    public void register(String key, MailGenerator mailBodyGenerator) {
        generatorMap.put(key, mailBodyGenerator);
    }

    public void register(String key, Sender someSender) {
        senderMap.put(key, someSender);
    }

    public void register(String key, ControllerNotification someCtrlNotification) {
        ctrlNotificationMap.put(key, someCtrlNotification);
    }


    @Transactional
    @Override
    public ItemsDto<Object> send(MailInfo mailInfo) {

        MailGenerator mailGenerator = initMailBodyGenerator(mailInfo);
        Sender mailSender = initSenderNotification(mailInfo);
        ControllerNotification ctrlNotification = initControllerNotification(mailInfo);

        List<Object> infos = new ArrayList<>();

        for (User user : mailInfo.getTo()) {
            if (ctrlNotification.isAvailable(mailInfo, user)) {
                String mail = mailGenerator.generate(mailInfo, user);

                mailSender.send(mail);
            }
            infos.add(ctrlNotification.info(user));
        }
        return new ItemsDto<Object>(infos);
    }

    public MailGenerator initMailBodyGenerator(MailInfo mailInfo) {
        String typeNotification = mailInfo.getTypeNotification() + mailInfo.getTypeMailSender();
        MailGenerator bodyGenerator = generatorMap.get(typeNotification);
        if (bodyGenerator == null)
            throw new UnsupportedOperationException(typeNotification);

        return bodyGenerator;
    }


    public Sender initSenderNotification(MailInfo mailInfo) {
        String typeSender = mailInfo.getTypeMailSender();
        Sender mailSender = senderMap.get(typeSender);
        if (mailSender == null)
            throw new UnsupportedOperationException(typeSender);

        return mailSender;
    }

    public ControllerNotification initControllerNotification(MailInfo mailInfo) {
        String typeCtrlNotif = mailInfo.getTypeNotification();
        ControllerNotification ctrlNotification = ctrlNotificationMap.get(typeCtrlNotif);
        if (ctrlNotification == null)
            throw new UnsupportedOperationException(typeCtrlNotif);

        return ctrlNotification;
    }

}

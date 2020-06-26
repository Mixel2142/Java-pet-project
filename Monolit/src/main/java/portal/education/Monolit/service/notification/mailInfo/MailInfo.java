package portal.education.Monolit.service.notification.mailInfo;

import portal.education.Monolit.data.model.person.User;

import java.util.List;

public interface MailInfo {

    User getFrom();

    // для массовой рассылки
    List<User> getTo();

    String getTypeNotification();

    String getTypeMailSender();

    String getJsonData();

    String getToAddress();

    void setToAddress(String toAddress);

    void setFrom(User user);

    void setTo(List<User> users);

    void setTypeNotification(String typeNotification);

    void setTypeMailSender(String typeMailSender);

    void setJsonData(String jsonData);
}

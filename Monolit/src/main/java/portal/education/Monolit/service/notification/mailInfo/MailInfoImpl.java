package portal.education.Monolit.service.notification.mailInfo;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.person.UserCrudService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/*
Несёт в себе всю информацию для получения из неё текста сообщения,
а также о способе отправки
 */
@Service
@Getter
@Setter
@NoArgsConstructor
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MailInfoImpl implements MailInfo {

    @Autowired
    UserCrudService userCrud;

    private User from;

    private List<User> to;

    private String toAddress;

    private String jsonData;

    private String typeNotification;

    private String typeMailSender;


    public MailInfoImpl(String typeNotification, String typeMailSender, String jsonData, User from, User... to) {
        setFrom(from);
        setTo(List.of(to));
        setTypeNotification(typeNotification);
        setTypeMailSender(typeMailSender);
        setJsonData(jsonData);
    }

    public void setFrom(User from) {

        Optional.ofNullable(from)
                .ifPresentOrElse(
                        user -> this.from = user,
                        () -> this.from = userCrud.getByNicknameOrNull("AdminTest")
                );
    }

}

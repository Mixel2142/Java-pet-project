package portal.education.Monolit.service.notification.controller;

import portal.education.Monolit.data.dto.UserAttemptToAccountConfirmDto;
import portal.education.Monolit.data.model.notification.PasswordConfirmation;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.notification.PasswordConfirmationRepository;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PasswordForgetNotification extends ConfirmNotification implements ControllerNotification {

    @Autowired
    private PasswordConfirmationRepository confNotDao;

    @Override
    public boolean isAvailable(MailInfo mailInfo, User user) { // проверка на запрещение отправки уведомлений (превышено ли допустимое кол-во сообщений)

        var passConfNotif = Optional.ofNullable(confNotDao.findByUser(user))
                .orElseGet(
                        () -> confNotDao.save(
                                new PasswordConfirmation(MAX_AMOUNT_CONFIRM_ATTEMPTS, user)
                        )
                );

        return super.isAvailable(user,
                passConfNotif,
                MAX_AMOUNT_CONFIRM_ATTEMPTS,
                TIME_INTERVAL_BETWEEN_GROUP_ATTEMPTS,
                TIME_INTERVAL_BETWEEN_ATTEMPTS);
    }

    @Override
    public Object info(User user) {
        return new UserAttemptToAccountConfirmDto(
                user.getNickname(),
                confNotDao.getAttempts(user),
                confNotDao.getNextAttemptTime(user)
        );
    }

    @Override
    public String getMyType() {
        return super.getMyType() + TypeNotification.PASSWORD_FORGET;
    }
}

















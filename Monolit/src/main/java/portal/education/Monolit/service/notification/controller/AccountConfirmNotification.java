package portal.education.Monolit.service.notification.controller;


import portal.education.Monolit.data.dto.UserAttemptToAccountConfirmDto;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.notification.AccountConfirmationRepository;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import portal.education.Monolit.service.person.AccountConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountConfirmNotification extends ConfirmNotification implements ControllerNotification {


    @Autowired
    private AccountConfirmationService accountConfirmationService;

    @Autowired
    private AccountConfirmationRepository confNotDao;

    @Override
    public boolean isAvailable(MailInfo mailInfo, User user) {

        var emailConfNotif = accountConfirmationService.findByUserOrCreate(
                MAX_AMOUNT_CONFIRM_ATTEMPTS,
                user,
                mailInfo.getTypeMailSender(),
                mailInfo.getToAddress()
        );

        return super.isAvailable(user,
                emailConfNotif,
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
        return super.getMyType() + TypeNotification.ACCOUNT;
    }


}

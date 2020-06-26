package portal.education.Monolit.service.notification.controller;

import portal.education.Monolit.data.dto.OkAndErrorUrlDto;
import portal.education.Monolit.data.model.abstractModel.AbstractConfirmation;
import portal.education.Monolit.data.model.notification.AccountConfirmation;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Transactional
public class ConfirmNotification {

    public static Long TIME_LIFE_ACCOUNT_CONFIRM_LINK;

    public static Long TIME_LIFE_PASSWORD_CONFIRM_LINK;

    public static Long TIME_INTERVAL_BETWEEN_ATTEMPTS;

    public static Long TIME_INTERVAL_BETWEEN_GROUP_ATTEMPTS;

    public static Short MAX_AMOUNT_CONFIRM_ATTEMPTS;

    public static String SUPPORT_EMAIL;

    protected boolean isAvailable(User user,
                                  AbstractConfirmation confirmNotification,
                                  Short Max_Amount_Confirm_Attempts,
                                  Long Time_Interval_Between_Group_Attempts,
                                  Long Time_Interval_Between_Attempts

    ) {

        if (confirmNotification.getNextAttemptTime().before(Date.from(Instant.now().plusMillis(1000)))) {
            if (confirmNotification.getAttempts() <= 1) {

                confirmNotification.setAttempts(Max_Amount_Confirm_Attempts);

                Date nextAttempt = Date.from(Instant.now().plusMillis(Time_Interval_Between_Group_Attempts));
                confirmNotification.setNextAttemptTime(nextAttempt);

            } else {
                confirmNotification.setAttempts((short) (confirmNotification.getAttempts() - 1));

                Date nextAttempt = Date.from(Instant.now().plusMillis(Time_Interval_Between_Attempts));
                confirmNotification.setNextAttemptTime(nextAttempt);

            }
            return true;
        }
        return false;
    }

    public static String generateConfirmationToken(JwtTokenUtil jwtTokenUtil, OkAndErrorUrlDto dto, AccountConfirmation confirmation, Long timeLifeLink) {
        return jwtTokenUtil.generateDataPayloadToken(
                confirmation.getUser().getNickname(),
                Map.of("redirectOk", dto.getRedirectOk(), "redirectError", dto.getRedirectError(), "accountIdentification", confirmation.getAccountIdentification(), "typeAccount", confirmation.getTypeAccount()),
                timeLifeLink
        );
    }

    public String getMyType() {
        return TypeNotification.CONFIRM;
    }

    @Value("${support.email}")
    public void setSupportEmail(String supportEmail) {
        this.SUPPORT_EMAIL = supportEmail;
    }

    @Value("${max.amount.confirm.attempts}")
    public void setMaxAmountConfirmAttempts(Short maxAmountConfirmAttempts) {
        MAX_AMOUNT_CONFIRM_ATTEMPTS = maxAmountConfirmAttempts;
    }

    @Value("${time.interval.between.attempts}")
    public void setTimeLifeIntervalBetweenAttempts(Long timeLifeIntervalBetweenAttempts) {
        TIME_INTERVAL_BETWEEN_ATTEMPTS = timeLifeIntervalBetweenAttempts * 1000;
    }

    @Value("${time.interval.between.group.attempts}")
    public void setTimeLifeIntervalBetweenGroupAttempts(Long timeLifeIntervalBetweenGroupAttempts) {
        TIME_INTERVAL_BETWEEN_GROUP_ATTEMPTS = timeLifeIntervalBetweenGroupAttempts * 1000;
    }

    @Value("${jwt.timeLife.EmailConfirmLink}")
    public void setTimeLifeEmailConfirmLink(Long timeLifeEmailConfirmLink) {
        TIME_LIFE_ACCOUNT_CONFIRM_LINK = timeLifeEmailConfirmLink * 1000;
    }

    @Value("${jwt.timeLife.PasswordConfirmLink}")
    public void setTimeLifePasswordConfirmLink(Long timeLifePasswordConfirmLink) {
        TIME_LIFE_PASSWORD_CONFIRM_LINK = timeLifePasswordConfirmLink * 1000;
    }
}

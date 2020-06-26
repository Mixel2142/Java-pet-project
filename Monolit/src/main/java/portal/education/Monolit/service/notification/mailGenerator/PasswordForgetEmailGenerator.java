package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.dto.OkAndErrorUrlDto;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.JsonObjectConverter;
import portal.education.Monolit.service.notification.controller.ConfirmNotification;
import portal.education.Monolit.service.notification.controller.TypeNotification;
import portal.education.Monolit.service.notification.sender.TypeMailSender;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import portal.education.Monolit.service.person.AccountConfirmationService;
import portal.education.Monolit.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordForgetEmailGenerator extends ConfirmNotification implements MailGenerator {

    final String greeting = " Вас приветствует TeenEducation portal!";

    final String info = "Это письмо было отправлено Вам в ответ на прозьбу изменить пароль: ";

    final String url = "/api/free/registry/password/forget/check?code=";

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JsonObjectConverter jsonObjectConverter;

    @Autowired
    ConfirmBodyBuilder confirmBodyBuilder;

    @Autowired
    EmailConstructHelper constructEmail;

    @Autowired
    AccountConfirmationService accountConfirmationService;

    @Override
    public String generate(MailInfo mailInfo, User user) {
        OkAndErrorUrlDto dto = jsonObjectConverter.jsonToObject(mailInfo.getJsonData(), OkAndErrorUrlDto.class);

        var confirmation = accountConfirmationService.findByUserOrThrow(user);

        String token = generateConfirmationToken(jwtTokenUtil, dto, confirmation, TIME_LIFE_PASSWORD_CONFIRM_LINK);

        String body = confirmBodyBuilder.build(user, token, dto, greeting, info, url);

        return jsonObjectConverter.objectToJson(constructEmail.constructEmail("Смена пароля", body, confirmation));
    }

    @Override
    public String getMyType() {
        return super.getMyType() + TypeNotification.PASSWORD_FORGET+ TypeMailSender.EMAIL;
    }

}

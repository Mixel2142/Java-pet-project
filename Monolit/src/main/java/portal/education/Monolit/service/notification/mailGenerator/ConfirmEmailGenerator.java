package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.dto.OkAndErrorUrlDto;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.JsonObjectConverter;
import portal.education.Monolit.service.notification.controller.ConfirmNotification;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import portal.education.Monolit.utils.JwtUtilForMonolit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static portal.education.Monolit.service.notification.controller.TypeNotification.ACCOUNT;
import static portal.education.Monolit.service.notification.controller.TypeNotification.CONFIRM;
import static portal.education.Monolit.service.notification.sender.TypeMailSender.EMAIL;

@Service
public class ConfirmEmailGenerator extends ConfirmNotification implements MailGenerator {


    final String greeting = " Вас приветствует TeenEducation portal!";

    final String info = "Это письмо было отправлено Вам в ответ на прозьбу привязать аккаунт: ";

    final String url = "/api/free/registry/account/check?code=";

    @Autowired
    JwtUtilForMonolit jwtUtilForMonolit;

    @Autowired
    JsonObjectConverter jsonObjectConverter;

    @Autowired
    ConfirmBodyBuilder confirmBodyBuilder;

    @Autowired
    EmailConstructHelper constructEmail;

//    @Autowired
//    AccountConfirmationService accountConfirmationService;

    @Override
    public String generate(MailInfo mailInfo, User user) {

        OkAndErrorUrlDto dto = jsonObjectConverter.jsonToObject(mailInfo.getJsonData(), OkAndErrorUrlDto.class);

////        var confirmation = accountConfirmationService.findByUserOrCreate(MAX_AMOUNT_CONFIRM_ATTEMPTS,user,mailInfo.getTypeMailSender(),mailInfo.getToAddress());
//
//        String token = generateConfirmationToken(jwtUtilForMonolit, dto, confirmation, TIME_LIFE_ACCOUNT_CONFIRM_LINK);
//
//        String body = confirmBodyBuilder.build(user, token, dto, greeting, info, url);
//
//        return jsonObjectConverter.objectToJson(constructEmail.constructEmail("Подтверждение почты", body, confirmation));
        return null;
    }

    @Override
    public String getMyType() {
        return CONFIRM + ACCOUNT + EMAIL;
    }
}

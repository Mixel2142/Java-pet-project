package portal.education.Monolit.service.notification.mailInfo;

import portal.education.Monolit.data.dto.*;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.JsonObjectConverter;
import portal.education.Monolit.service.notification.controller.TypeNotification;
import portal.education.Monolit.service.notification.sender.TypeMailSender;
import portal.education.Monolit.service.person.AccountConfirmationService;
import portal.education.Monolit.service.person.UserCrudService;
import portal.education.Monolit.service.person.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MailInfoFactoryIml implements MailInfoFactory {

    @Autowired
    JsonObjectConverter jsonObjectConverter;

    @Autowired
    ApplicationContext context;

    @Autowired
    UserCrudService userCrud;

    @Autowired
    UserService userService;

//    @Autowired
//    AccountConfirmationService accountConfirmationService;

    @Override
    public MailInfo buildFromAccountConfirmDto(AccountConfirmDto dto) {

        var okAndErrorUrlDto = new OkAndErrorUrlDto(dto.getRedirectOk(), dto.getRedirectError(), dto.getRequest());
        String jsonOkAndErrorUrlDto = jsonObjectConverter.objectToJson(okAndErrorUrlDto);

        MailInfo mailInfo = context.getBean(MailInfo.class);

        mailInfo.setFrom(null);
        mailInfo.setTo(List.of(dto.getFrom()));
        mailInfo.setTypeNotification(dto.getTypeNotification());
        mailInfo.setTypeMailSender(dto.getTypeMailSender());
        mailInfo.setJsonData(jsonOkAndErrorUrlDto);
        mailInfo.setToAddress(dto.getTo());
        return mailInfo;
    }

    @Override
    public MailInfo buildFromEmailConfirmDto(EmailConfirmDto dto, User user, HttpServletRequest request) {

        var acConfDto = new AccountConfirmDto(dto.getRedirectOk(), dto.getRedirectError(), null, user, dto.getEmail(), TypeNotification.CONFIRM+ TypeNotification.ACCOUNT, TypeMailSender.EMAIL);

        User usr = userCrud.getByNicknameOrNull(user.getNickname());
        usr.setAccountIdentification(acConfDto.getTo());
        usr.setAccountConfirmed(false);
        acConfDto.setFrom(usr);
        acConfDto.setRequest(new HttpRequestUrlsDto(request));
        return buildFromAccountConfirmDto(acConfDto);
    }

    @Override
    public MailInfo buildFromPasswordConfirmDto(PasswordForgetDto dto, HttpServletRequest request) {
        var req = new HttpRequestUrlsDto(request);
        return buildFromPasswordConfirmDto(dto, req);
    }

    public MailInfo buildFromPasswordConfirmDto(PasswordForgetDto dto, HttpRequestUrlsDto request) {

        User user = userService.findByNickname(dto.getNickname());

//        var acConf = accountConfirmationService.findByUserOrThrow(user);

        var okAndErrorUrlDto = new OkAndErrorUrlDto(dto.getRedirectOk(), dto.getRedirectError(), request);
        String jsonOkAndErrorUrlDto = jsonObjectConverter.objectToJson(okAndErrorUrlDto);

        MailInfo mailInfo = context.getBean(MailInfo.class);

        mailInfo.setFrom(null);
        mailInfo.setTo(List.of(user));
        mailInfo.setTypeNotification(TypeNotification.CONFIRM + TypeNotification.PASSWORD_FORGET);
//        mailInfo.setTypeMailSender(acConf.getTypeAccount());
        mailInfo.setJsonData(jsonOkAndErrorUrlDto);

        return mailInfo;
    }

}

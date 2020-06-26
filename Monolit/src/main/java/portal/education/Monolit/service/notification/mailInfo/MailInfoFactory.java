package portal.education.Monolit.service.notification.mailInfo;

import portal.education.Monolit.data.dto.AccountConfirmDto;
import portal.education.Monolit.data.dto.EmailConfirmDto;
import portal.education.Monolit.data.dto.PasswordForgetDto;
import portal.education.Monolit.data.model.person.User;

import javax.servlet.http.HttpServletRequest;

public interface MailInfoFactory {


    MailInfo buildFromAccountConfirmDto(AccountConfirmDto dto);

    MailInfo buildFromEmailConfirmDto(EmailConfirmDto dto, User user, HttpServletRequest request);

    MailInfo buildFromPasswordConfirmDto(PasswordForgetDto dto, HttpServletRequest request);
}

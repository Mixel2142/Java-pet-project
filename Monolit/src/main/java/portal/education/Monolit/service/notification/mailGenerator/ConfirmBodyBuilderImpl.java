package portal.education.Monolit.service.notification.mailGenerator;

import portal.education.Monolit.data.dto.OkAndErrorUrlDto;
import portal.education.Monolit.data.dto.HttpRequestUrlsDto;
import portal.education.Monolit.data.model.person.User;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ConfirmBodyBuilderImpl implements ConfirmBodyBuilder {


    @Override
    public String build(User user, String token, OkAndErrorUrlDto dto, String greeting, String infoMsg, String url) {
        return buildMessage(buildGreeting(user, greeting), buildInfo(user, infoMsg), buildLink(user, token, dto.getRequest(), url));
    }


    private String buildMessage(StringBuilder greeting, StringBuilder info, StringBuilder link) {
        return greeting
                .append('\n')
                .append(info)
                .append('\n')
                .append(link)
                .toString();
    }

    private StringBuilder buildGreeting(User user, String greetingMsg) {
        StringBuilder greeting = new StringBuilder();

        return greeting.append(user.getNickname())
                .append(',')
                .append(greetingMsg);
    }

    private StringBuilder buildInfo(User user, String infoMsg) {
        StringBuilder info = new StringBuilder();
        return info.append(infoMsg)
                .append(user.getNickname())
                .append("\n Для выполнения дествия перейдите по ссылке:");
    }


    private StringBuilder buildLink(User user, String token, HttpRequestUrlsDto request, String url) {
        StringBuilder link = new StringBuilder();

        return link.append(buildAppUrl(user, request))
                .append(url)
                .append(Base64.getEncoder().encodeToString(token.getBytes()));
    }


    public StringBuilder buildAppUrl(User user, HttpRequestUrlsDto request) {
        StringBuilder appUrl = new StringBuilder();

        return appUrl.append("http://")
                .append(request.getServerName())
                .append(':')
                .append(request.getServerPort())
                .append(request.getContextPath());
    }

}

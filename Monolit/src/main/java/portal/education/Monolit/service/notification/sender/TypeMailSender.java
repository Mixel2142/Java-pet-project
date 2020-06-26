package portal.education.Monolit.service.notification.sender;

import portal.education.Monolit.utils.ExcMsg;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class TypeMailSender {

    public final static String EMAIL = "Email";


    @Hidden
    private final static List<String> list = List.of(
            EMAIL
    );

    public static boolean containsOrElseThrow(String status) {
        if (!list.contains(status))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExcMsg.ArticleStatusNotFound(status));
        return true;
    }
}

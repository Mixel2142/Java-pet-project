package portal.education.Monolit.service.notification.controller;

import portal.education.Monolit.utils.ExcMsg;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class TypeNotification {

    public final static String CONFIRM = "Confirm";
    public final static String PASSWORD_FORGET = "PasswordForget";
    public final static String ACCOUNT = "Account";

    @Hidden
    private final static List<String> list = List.of(
            CONFIRM,
            PASSWORD_FORGET,
            ACCOUNT
    );

    public static boolean containsOrElseThrow(String status) {
        if (!list.contains(status))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExcMsg.TypeNotificationNotFound(status));
        return true;
    }
}

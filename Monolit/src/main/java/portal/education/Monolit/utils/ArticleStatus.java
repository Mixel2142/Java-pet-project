package portal.education.Monolit.utils;/*
package portal.education.Monolit.utils;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public final class ArticleStatus {
    public final static String PUBLISHED = "Published";
    public final static String AWAITING_PUBLICATION = "Awaiting publication";
    public final static String PUBLICATION_DENIED = "Publication denied";
    public final static String IN_DEVELOPING = "In developing";
    public final static String ALL = "All";

    @Hidden
    public final static List<String> list = List.of(
            PUBLISHED,
            AWAITING_PUBLICATION,
            PUBLICATION_DENIED,
            IN_DEVELOPING,
            ALL
    );

    public static boolean containsOrElseThrow(String status) {
        if (!list.contains(status))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ExcMsg.ArticleStatusNotFound(status));
        return true;
    }
}
*/

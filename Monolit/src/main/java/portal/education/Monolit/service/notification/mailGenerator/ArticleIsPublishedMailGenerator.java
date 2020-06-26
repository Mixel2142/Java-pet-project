package portal.education.Monolit.service.notification.mailGenerator;


import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.notification.mailInfo.MailInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ArticleIsPublishedMailGenerator implements MailGenerator {


    private Map<String, MailGenerator> generatorMap = new HashMap<>();



    @Override
    public String generate(MailInfo mailInfo, User user) {


        // TODO ...

        return "article published";
    }

    @Override
    public String getMyType() {
        return "Article is published";
    }
}

package portal.education.Monolit.data.repos.notification;

import portal.education.Monolit.data.model.abstractModel.AbstractConfirmation;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.abstractRepos.AbstractNotificationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Date;


@NoRepositoryBean
public interface ConfirmNotificationMRepository<E extends AbstractConfirmation> extends AbstractNotificationRepository<E> {
}

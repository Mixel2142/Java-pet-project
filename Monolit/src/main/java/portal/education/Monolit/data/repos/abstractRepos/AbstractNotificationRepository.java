package portal.education.Monolit.data.repos.abstractRepos;

import portal.education.Monolit.data.model.abstractModel.AbstractNotification;
import portal.education.Monolit.data.repos.abstractRepos.AbstractEntityRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface AbstractNotificationRepository<E extends AbstractNotification> extends AbstractEntityRepository<E,Long> {


}

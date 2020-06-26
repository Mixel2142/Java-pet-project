package portal.education.Monolit.data.repos.abstractRepos;

import portal.education.Monolit.data.model.abstractModel.AbstractView;
import portal.education.Monolit.data.model.person.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;


@NoRepositoryBean
public interface AbstractViewRepository<E extends AbstractView> extends AbstractEntityRepository<E, Long> {

    Optional<List<E>> findByUserOrderByDateDesc(User user, Pageable pageable);
}

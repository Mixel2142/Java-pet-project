package portal.education.Monolit.data.repos.abstractRepos;

import portal.education.Monolit.data.model.abstractModel.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractEntityRepository<E extends AbstractEntity,ID extends Serializable> extends JpaRepository<E, ID> {

    Optional<E> findById(ID id);

}

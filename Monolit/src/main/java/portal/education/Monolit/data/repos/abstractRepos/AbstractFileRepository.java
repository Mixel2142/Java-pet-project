package portal.education.Monolit.data.repos.abstractRepos;


import portal.education.Monolit.data.model.abstractModel.AbstractFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;


@NoRepositoryBean
public interface AbstractFileRepository<E extends AbstractFile> extends JpaRepository<E, UUID> {

    Optional<E> findById(UUID id);
}

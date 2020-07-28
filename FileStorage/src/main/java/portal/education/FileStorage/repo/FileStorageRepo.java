package portal.education.FileStorage.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import portal.education.FileStorage.domain.File;

import java.util.UUID;

@Repository
public interface FileStorageRepo extends ReactiveCrudRepository<File, UUID> {

}

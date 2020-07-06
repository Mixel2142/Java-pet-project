package portal.education.AuthService.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import portal.education.AuthService.domain.AuthData;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AuthRepo extends ReactiveCrudRepository<AuthData, UUID> {

    Mono<AuthData> findByLogin(String login);
}

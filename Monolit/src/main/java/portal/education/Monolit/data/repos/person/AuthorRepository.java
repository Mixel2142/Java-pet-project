package portal.education.Monolit.data.repos.person;


import portal.education.Monolit.data.model.person.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuthorRepository extends UserRepository<Author> {

    Optional<Author> findByNickname(String nickname);

    Optional<Author> findByAccountIdentification(String identification);

    Optional<List<Author>> findAllByOrderByRatingDesc(Pageable pageable);

    Optional<List<Author>> findByNicknameStartingWithOrderByRatingDesc(String partOfNickName, Pageable pageable);


}

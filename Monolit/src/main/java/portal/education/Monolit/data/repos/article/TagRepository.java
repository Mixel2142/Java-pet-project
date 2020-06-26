package portal.education.Monolit.data.repos.article;


import portal.education.Monolit.data.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    void deleteByName(String name);

    Optional<List<Tag>> findAllByOrderByRatingDesc(Pageable pageable);

    Optional<List<Tag>> findByNameStartingWithOrderByRatingDesc(String name);

    List<Tag> findByNameStartingWith(String name, Pageable pageable);
    List<Tag> findByNameStartingWith(String name);
}

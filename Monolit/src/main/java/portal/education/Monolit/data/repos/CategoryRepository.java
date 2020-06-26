package portal.education.Monolit.data.repos;


import portal.education.Monolit.data.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    void deleteByName(String name);

    Optional<List<Category>> findAllByOrderByRatingDesc(Pageable pageable);

    Optional<List<Category>> findByNameStartingWithOrderByRatingDesc(String partOfName);

    List<Category> findByNameStartingWith(String name, Pageable pageable);

    Long countByNameStartingWith(String partOfName);
}

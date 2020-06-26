package portal.education.Monolit.data.repos.article;


import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.person.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Optional<Article> findByTitle(String title);

    Optional<List<Article>> findByPublishedTrueOrderByRatingDesc(Pageable pageable);

    Optional<List<Article>> findByPublishedTrue(Pageable pageable);

    Optional<List<Article>> findByPublishedTrueAndCategoryOrderByRatingDesc(Category category, Pageable pageable);

    Optional<List<Article>> findByPublishedTrueAndAuthorOrderByRatingDesc(Author author, Pageable pageable);

    Optional<List<Article>> findByPublishedTrueAndTitleStartingWithOrderByRatingDesc(String partOfTitle, Pageable pageable);

    Optional<List<Article>> findByAuthorAndStatus(Author author, String status, Pageable pageable);

    Optional<List<Article>> findByAuthor(Author author, Pageable pageable);

    void deleteByTitle(String title) throws DataAccessException;

    Optional<List<Article>> findByAuthor(Author author);

    Optional<List<Article>> findByCategory(Category category);

    Optional<List<Article>> findByOrderByRatingDesc(Pageable pageable);

    Optional<List<Article>> findByCategoryOrderByRatingDesc(Category category, Pageable pageable);

    Optional<List<Article>> findByAuthorOrderByRatingDesc(Author author, Pageable pageable);

    Optional<List<Article>> findByTitleStartingWithOrderByRatingDesc(String partOfTitle, Pageable pageable);

    Long countByAuthorAndStatus(Author author, String status);

    Long countByCategory(Category category);

    Long countByAuthor(Author author);

    Long countByPublishedTrueAndTitleStartingWith(String partOfTitle);

    Long countByPublishedTrue();
}

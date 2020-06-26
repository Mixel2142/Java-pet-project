package portal.education.Monolit.data.repos.comment;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.comment.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByArticleOrderByLikesCounterDesc(Article article, Pageable pageable);

    Optional<List<Comment>> findByArticleOrderByCreatedOnDesc(Article article, Pageable pageable);

    List<Comment> findByArticleId(UUID articleId, Pageable pageable);

    Long countByArticle(Article article);
}

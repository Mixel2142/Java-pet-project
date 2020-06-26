package portal.education.Monolit.data.repos.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleLike;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.abstractRepos.AbstractLikeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ArticleLikeRepository extends AbstractLikeRepository<ArticleLike> {
    Optional<ArticleLike> findByUserAndArticle(User user, Article article);
}

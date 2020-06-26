package portal.education.Monolit.service.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleLike;
import portal.education.Monolit.data.model.person.User;

public interface ArticleLikeCrudService {

    ArticleLike getByUserAndArticle(User user, Article article);

    ArticleLike put(ArticleLike articleLike);
}

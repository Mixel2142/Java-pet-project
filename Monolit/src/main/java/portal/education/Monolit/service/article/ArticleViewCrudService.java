package portal.education.Monolit.service.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleView;
import portal.education.Monolit.data.model.person.User;

public interface ArticleViewCrudService {
    ArticleView putView(Article article, User user, Long timeView);
    ArticleView getViewByUserAndArticleOrNull(Article article, User user);
}

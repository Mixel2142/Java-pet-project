package portal.education.Monolit.data.repos.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleView;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.abstractRepos.AbstractViewRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ArticleViewRepository extends AbstractViewRepository<ArticleView> {

    ArticleView findByArticleAndUser(Article article, User user);
}

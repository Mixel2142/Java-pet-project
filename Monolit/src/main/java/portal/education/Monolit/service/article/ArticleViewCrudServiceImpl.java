package portal.education.Monolit.service.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleView;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.article.ArticleViewRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@NoArgsConstructor
@Transactional
public class ArticleViewCrudServiceImpl implements ArticleViewCrudService {


    @Autowired
    private ArticleViewRepository viewDao;

    @Override
    public ArticleView putView(Article article, User user, Long timeView) {
        return viewDao.save(new ArticleView(article, user, timeView));
    }

    @Override
    public ArticleView getViewByUserAndArticleOrNull(Article article, User user) {
        return viewDao.findByArticleAndUser(article,user);
    }
}

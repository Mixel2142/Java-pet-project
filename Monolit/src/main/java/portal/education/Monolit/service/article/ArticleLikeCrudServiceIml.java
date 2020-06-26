package portal.education.Monolit.service.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleLike;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.article.ArticleLikeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@NoArgsConstructor
@Transactional
public class ArticleLikeCrudServiceIml implements ArticleLikeCrudService {

    @Autowired
    private ArticleLikeRepository articleLikeDao;

    @Override
    public ArticleLike getByUserAndArticle(User user, Article article) {
        return articleLikeDao.findByUserAndArticle(user, article)
                .orElseGet(() -> new ArticleLike(user, article));
    }

    @Override
    public ArticleLike put(ArticleLike articleLike) {
        return articleLikeDao.save(articleLike);
    }
}

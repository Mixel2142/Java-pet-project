package portal.education.Monolit.task;

import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.service.CategoryCrudService;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.service.person.AuthorCrudService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    private final static Integer size = 100;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ArticleCrudService articleCrud;

    @Autowired
    private AuthorCrudService authorCrud;

    @Autowired
    private CategoryCrudService categoryCrud;


    @Override
    public void reCalcRatingArticle() {
        try {

            Integer page = -1;
            while (true) {
                page++;
                var items = articleCrud.getAllPublished(page, size);

                if (items.getItems().isEmpty())
                    break;

                List<Article> articles = items.getItems();
                for (Article article : articles) {
                    article.setRating(
                            article.getViewsCounter() +
                                    article.getComments().size() +
                                    (article.getLikesCounter() * 2)
                    );
                    entityManager.flush();
                }
                if (items.getItems().size() != size)
                    break;
            }
        } catch (Exception ex) {
            log.error("reCalcRatingArticle не удался");
        }
    }


    @Override
    public void reCalcRatingAuthor() {
        try {
            Integer page = -1;
            while (true) {
                page++;
                var items = authorCrud.getAll(page, size);

                if (items.getItems().isEmpty())
                    break;

                List<Author> authors = items.getItems();
                for (Author author : authors) {
                    author.setRating(sumRatingsFromArticleByAuthor(author.getId()));
                    entityManager.flush();
                }
                if (items.getItems().size() != size)
                    break;
            }
        } catch (Exception ex) {
            log.error("reCalcRatingAuthor не удался");
        }
    }


    @Override
    public void reCalcRatingCategory() {
        try {

            Integer page = -1;
            while (true) {
                page++;
                var items = categoryCrud.getAll(page, size);

                if (items.getItems().isEmpty())
                    break;

                List<Category> categories = items.getItems();
                for (Category category : categories) {
                    category.setRating(sumRatingsFromArticlesByCategory(category.getId()));
//                    entityManager.flush();
                }
                if (items.getItems().size() != size)
                    break;
            }
            entityManager.flush();
        } catch (RuntimeException ex) {
            log.error("reCalcRatingCategory не удался");
        }
    }


    private Long sumRatingsFromArticleByAuthor(UUID authorId) {
        try {

            Integer page = -1;
            while (true) {
                page++;
                var items = articleCrud.getTopByAuthor(page, size, authorId);
                if (items.getItems().isEmpty())
                    break;

                return summRatingArticles(items.getItems());
            }
            return 0L;
        } catch (Exception ex) {
            entityManager.flush();
            log.debug("sumRatingsFromArticlesByAuthor не удался");
            return 0L;
        }
    }


    private Long sumRatingsFromArticlesByCategory(Long categoryId) {
        try {
            Integer page = -1;
            while (true) {
                page++;
                var items = articleCrud.getTopByCategory(categoryId, page, size);

                if (items.getItems().isEmpty())
                    break;

                return summRatingArticles(items.getItems());
            }
            return 0L;
        } catch (RuntimeException ex) {
            entityManager.flush();
            log.debug("sumRatingsFromArticlesByCategory не удался");
            return 0L;
        }
    }


    private Long summRatingArticles(List<Article> articles){
        Long rating = 0L;
        for (Article article : articles) {
            rating += article.getRating();
        }
        return rating;
    }
}

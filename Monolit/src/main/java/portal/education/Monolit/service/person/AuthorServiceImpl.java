package portal.education.Monolit.service.person;

import portal.education.Monolit.data.dto.ArticleDto;
import portal.education.Monolit.data.dto.ArticleStatusDto;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleStatus;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.service.CategoryCrudService;
import portal.education.Monolit.service.TagCrudService;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.service.article.ArticleFileCrudService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryCrudService categoryCrud;

    @Autowired
    private TagCrudService tagCrud;

    @Autowired
    private ArticleCrudService articleCrud;

    @Autowired
    private ArticleFileCrudService articleFileCrud;

    @Override
    public void deleteArticle(Author author, UUID articleId) {
        if (articleCrud.getById(articleId)
                .getAuthor()
                .getId()
                .equals(author.getId())
        )
            articleCrud.deleteById(articleId);
    }

    @Override
    public Article patch(Author author, UUID articleId, ArticleDto articleDto) {
        articleDto.setAuthorId(author.getId());
        articleDto.setArticleId(articleId);
        if (articleCrud.getById(articleId).getAuthor().equals(author)) {
            return articleCrud.patch(articleDto);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Вы не автор этой статьи!");
        }
    }

    @Override
    public Article patchStatus(Author author, UUID articleId, ArticleStatus status) {

        if (articleCrud.getById(articleId).getAuthor().equals(author)) {
            return articleCrud.patchStatus(articleId, status);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    "Вы не автор этой статьи!");
        }
    }

    @Override
    public Article put(ArticleDto articleDto) {
        return articleCrud.put(articleDto);
    }

    @Override
    public ItemsDto<Article> findByAuthorAndStatus(Author author, ArticleStatusDto status) {

        var items = new ItemsDto<Article>();
        items.setTotal(articleCrud.countByAuthorAndStatus(author, status.getStatus()));
        items.setItems(articleCrud.getByAuthorAndStatusForAuthor(author, status));


        return items;
    }
}


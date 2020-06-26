package portal.education.Monolit.service.person;

import portal.education.Monolit.data.dto.ArticleDto;
import portal.education.Monolit.data.dto.ArticleStatusDto;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleStatus;
import portal.education.Monolit.data.model.person.Author;

import java.util.UUID;

// действия автора!!!
public interface AuthorService {

    void deleteArticle(Author author, UUID articleId);

    Article patch(Author author, UUID articleId, ArticleDto newArticle);

    Article put(ArticleDto articleDto);

    ItemsDto<Article> findByAuthorAndStatus(Author author, ArticleStatusDto status);

    public Article patchStatus(Author author, UUID articleId, ArticleStatus status);

}

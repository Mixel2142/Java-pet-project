package portal.education.Monolit.service.article;

import portal.education.Monolit.data.dto.ArticleDto;
import portal.education.Monolit.data.dto.ArticleStatusDto;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.dto.ViewDto;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleStatus;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.data.model.person.User;

import java.util.List;
import java.util.UUID;


public interface ArticleCrudService {

    Long countByAuthorForAuthor(Author author);

    List<Article> getByAuthorForAuthor(Author author, Integer indexPage, Integer sizePage);

    Long countByAuthorAndStatus(Author author, String status);

    List<Article> getByAuthorAndStatusForAuthor(Author author, ArticleStatusDto status);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ItemsDto<Article> getAllPublished(Integer page, Integer size);

     Article getByTitleOrNull(String title);

    Article put(Article article);

    Article patch(ArticleDto article);

    Article patchStatus(UUID articleId, ArticleStatus status);

    void deleteById(UUID articleId);

    Article put(ArticleDto articleDto);

    void addView(ViewDto viewDto);

    Long doLike(User user, UUID articleId, boolean like);

    Article getById(UUID articleId);

    Article getByTitle(String title);

    List<String> getAllImgFromBody(String body);

    ItemsDto<Article> getTopByCategory(Long categoryId, Integer page, Integer size);

    ItemsDto<Article> getTopByRating(Integer page, Integer size);

    ItemsDto<Article> getTopByAuthor(int page, int size, UUID authorId);

    ItemsDto<Article> getTopByAlphabet(int numberPage, int sizePage, String partOfTitle);

}

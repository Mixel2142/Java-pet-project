package portal.education.Monolit.service.article;

import portal.education.Monolit.data.dto.ArticleDto;
import portal.education.Monolit.data.dto.ArticleStatusDto;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.dto.ViewDto;
import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.model.Tag;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleLike;
import portal.education.Monolit.data.model.article.ArticleStatus;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.article.ArticleRepository;
import portal.education.Monolit.service.CategoryCrudService;
import portal.education.Monolit.service.TagCrudService;
import portal.education.Monolit.service.person.AuthorCrudService;
import portal.education.Monolit.service.person.UserCrudService;
import portal.education.Monolit.utils.ExcMsg;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class ArticleCrudServiceIml implements ArticleCrudService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleDao;

    @Autowired
    private UserCrudService userCrud;

    @Autowired
    private AuthorCrudService authorCrud;

    @Autowired
    private ArticleViewCrudService articleViewCrud;

    @Autowired
    private ArticleLikeCrudService articleLikeCrud;

    @Autowired
    private ArticleFileCrudService articleFileCrud;

    @Autowired
    private CategoryCrudService categoryCrud;

    @Autowired
    private TagCrudService tagCrud;


    @Override
    public void deleteById(UUID articleId) {
        entityManager.remove(getById(articleId));
    }

    @Override
    public void addView(ViewDto viewDto) {

        Article article = getById(viewDto.getArticleId());

        User user = userCrud.getByNicknameOrNull(viewDto.getNickName());

        if (user != null)
            if (articleViewCrud.getViewByUserAndArticleOrNull(article, user) != null)
                return;// Все зарегистрированные пользователи могут просмотреть только раз!

        articleViewCrud.putView(article, user, viewDto.getTimeView());
        article.setViewsCounter(article.getViewsCounter() + 1);

        entityManager.flush();
    }

    @Override
    public Long doLike(User user, UUID articleId, boolean like) {

        Article article = this.getById(articleId);

        ArticleLike likeArt = articleLikeCrud.getByUserAndArticle(user, article);

        Optional<Boolean> bLike = Optional.of(likeArt).flatMap(likeComment -> Optional.ofNullable(likeComment.isValue()));

        bLike.ifPresentOrElse(
                aBoolean -> {
                    if (aBoolean.booleanValue() != like)
                        article.setLikesCounter(article.getLikesCounter() + (like ? 1 : -1));
                },
                () -> {
                    article.setLikesCounter(article.getLikesCounter() + (like ? 1 : -1));
                }
        );

        likeArt.setValue(like);

        articleLikeCrud.put(likeArt);

        return article.getLikesCounter();
    }

    @Override
    public Article patch(ArticleDto articleDto) {

        Category category = categoryCrud.getByName(articleDto.getCategory());
        Set<Tag> tags = tagCrud.getByNames(articleDto.getTags());
        Article article = getByTitle(articleDto.getTitle());

        article.setCategory(category);
        article.setTags(tags);
        article.setTitle(articleDto.getTitle());
        article.setBody(articleDto.getBody());

        articleDao.save(article);
        entityManager.flush();
        return article;
    }

    @Override
    public Article patchStatus(UUID articleId, ArticleStatus status) {
        Article article = this.getById(articleId);
        article.setStatus(status);
        return articleDao.saveAndFlush(article);
    }

    @Override
    public Article put(ArticleDto articleDto) {

        Author author = authorCrud.getById(articleDto.getAuthorId());

        Article article = getByTitleOrNull(articleDto.getTitle());

        if (article != null) {
            if (article.getAuthor().equals(author)) {
                return this.patch(articleDto);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ExcMsg.ArticleAlreadyExist(article.getTitle()));
            }
        }

        Category category = categoryCrud.getByName(articleDto.getCategory());

        Set<Tag> tags = tagCrud.getByNames(articleDto.getTags());


        Article articleNew = new Article(author, articleDto.getTitle(), articleDto.getBody(), category, tags);

        articleDao.save(articleNew);

        List<String> list = getAllImgLongFromBody(articleDto.getBody());

        for (String id : list) {
            articleFileCrud.getById(UUID.fromString(id)).setArticle(articleNew);
        }

        entityManager.flush();

        return articleNew;
    }

    @Override
    public ItemsDto<Article> getAllPublished(Integer page, Integer size) {
        Pageable pagination = PageRequest.of(page, size);
        var dto = new ItemsDto<Article>();

        dto.setTotal(articleDao.countByPublishedTrue());

        dto.setItems(articleDao.findByPublishedTrue(pagination)
                .orElse(List.of()));
        return dto;
    }

    @Override
    public Article put(Article article) {
        List<String> list = getAllImgLongFromBody(article.getBody());

        for (String id : list) {
            articleFileCrud.getById(UUID.fromString(id)).setArticle(article);
        }
        return articleDao.save(article);
    }

    @Override
    public ItemsDto<Article> getTopByCategory(Long categoryId, Integer page, Integer size) {
        Pageable pagination = PageRequest.of(page, size);
        var dto = new ItemsDto<Article>();

        Category category = categoryCrud.getById(categoryId);

        dto.setTotal(articleDao.countByCategory(category));

        dto.setItems(articleDao.findByPublishedTrueAndCategoryOrderByRatingDesc(categoryCrud.getById(categoryId), pagination)
                .orElse(List.of()));

        return dto;
    }

    @Override
    public ItemsDto<Article> getTopByRating(Integer page, Integer size) {
        return this.getAllPublished(page, size);
    }

    @Override
    public List<String> getAllImgFromBody(String body) {
        Document doc = Jsoup.parse(body);

        List<String> list = doc.body().getElementsByTag("img").eachAttr("src").stream()
                .distinct()
                .collect(Collectors.toList());

        List<String> pureList = new ArrayList<>();
        for (String str : list) {
            pureList.add(str.substring(str.lastIndexOf('/') + 1));
        }
        return pureList;
    }

    @Override
    public ItemsDto<Article> getTopByAuthor(int page, int size, UUID authorId) {
        Pageable pagination = PageRequest.of(page, size);
        var dto = new ItemsDto<Article>();

        Author author = authorCrud.getById(authorId);

        dto.setTotal(articleDao.countByAuthor(author));

        dto.setItems(articleDao.findByPublishedTrueAndAuthorOrderByRatingDesc(author, pagination)
                .orElse(List.of()));

        return dto;
    }

    @Override
    public ItemsDto<Article> getTopByAlphabet(int page, int size, String partOfTitle) {
        Pageable pagination = PageRequest.of(page, size);
        var items = new ItemsDto<Article>();

        items.setTotal(articleDao.countByPublishedTrueAndTitleStartingWith(partOfTitle));

        items.setItems(articleDao.findByPublishedTrueAndTitleStartingWithOrderByRatingDesc(partOfTitle, pagination)
                .orElse(List.of(null)));
        return items;
    }

    @Override
    public Long countByAuthorAndStatus(Author author, String status) {
        return articleDao.countByAuthorAndStatus(author, status);
    }

    @Override
    public List<Article> getByAuthorAndStatusForAuthor(Author author, ArticleStatusDto status) {
        Pageable pagination = PageRequest.of(status.getIndexPage(), status.getSizePage());
        return articleDao.findByAuthorAndStatus(author, status.getStatus(), pagination)
                .orElse(List.of());
    }

    @Override
    public List<Article> getByAuthorForAuthor(Author author, Integer indexPage, Integer sizePage) {
        Pageable pagination = PageRequest.of(indexPage, sizePage);
        return articleDao.findByAuthor(author, pagination)
                .orElse(List.of());
    }

    @Override
    public Long countByAuthorForAuthor(Author author) {
        return articleDao.countByAuthor(author);
    }


    @Override
    public Article getById(UUID articleId) {
        return articleDao.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        ExcMsg.ArticleNotFound(articleId)
                ));
    }

    @Override
    public Article getByTitle(String title) {
        return articleDao.findByTitle(title)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            ExcMsg.ArticleNotFound(title)
                    );
                });
    }

    @Override
    public Article getByTitleOrNull(String title) {
        return articleDao.findByTitle(title)
                .orElse(null);
    }

    private List<String> getAllImgLongFromBody(String body) {

        Document doc = Jsoup.parse(body);

        List<String> list = doc.body().getElementsByTag("img").eachAttr("src").stream()
                .distinct()
                .collect(Collectors.toList());

        List<String> pureList = new ArrayList<>();
        for (String str : list) {
            pureList.add(str.substring(str.lastIndexOf('/') + 1));
        }
        return pureList;
    }

}

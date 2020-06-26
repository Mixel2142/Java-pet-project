package portal.education.Monolit.controller;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleStatus;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.article.ArticleRepository;
import portal.education.Monolit.data.repos.person.UserRepository;
import portal.education.Monolit.service.CommentCrudService;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.service.person.UserCrudService;
import portal.education.Monolit.utils.ExcMsg;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;


@Transactional
@RestController()
@RequestMapping("/admin")
@Tag(name = "Admin's actions", description = "All methods need access token.")
public class AdminController {

    @Autowired
    private ArticleRepository ArticleDao;

    @Autowired
    private ArticleRepository articleDao;

    @Autowired
    private ArticleCrudService articleCrud;

    @Autowired
    private UserRepository<User> userDao;

    @Autowired
    private UserCrudService userCrud;

    @Autowired
    private CommentCrudService commentCrud;

 /*
     @Autowired
    NotificationSenderService notificationSenderService;

  @Autowired
    EmailSender11 emailSender;

    @Autowired
    ArticleStatusChangeNotificationS articleStatusChangeNotificationS;*/

    ///////////////////////////////////////////////////////////////////////////////////////// ARTICLE PATCH
    @Operation(summary = "Сделать подтверждение статьи или отменить подтверждение.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    headers = @Header(
                            name = "status",
                            schema = @Schema(
                                    implementation = ArticleStatus.class,
                                    description = "Список всех возможных статусов."
                            )
                    )
            )})
    @PatchMapping("/article/status/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public Article patchArticleStatus(@PathVariable UUID articleId, @RequestParam ArticleStatus status) {

        Article article = articleCrud.patchStatus(articleId, status);

        /*
        notificationSenderService.send(
                    emailSender,
                    ArticleStatusChangeNotificationS.init(request, article),
                    user);
         */

        return article;
    }

    ////////////////////////////////////////////////////////////////////////////////////////// ARTICLE DELETE
    @Operation(summary = "Удаляет статью")
    @DeleteMapping(value = {"/article/{articleId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteArticle(@PathVariable UUID articleId) {
        articleCrud.deleteById(articleId);
    }

    ///////////////////////////////////////////////////////////////////////////////////////// USER PATCH
    @Operation(summary = "Забанить пользователя до какого либо времени или разбанить")
    @PatchMapping("/user/accountEnable/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void patchUser(@PathVariable UUID userId, @RequestParam boolean accountEnable, @RequestParam Date date) {
        userDao.findById(userId)
                .ifPresentOrElse(
                        u -> {
                            u.setAccountEnabled(accountEnable);
                            u.setLockoutEnd(date);
                        },
                        () -> {
                            throw new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    ExcMsg.UserNotFound(userId.toString())
                            );
                        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////// COMMENT DELETE
    @Operation(summary = "Удаляет комментарий")
    @DeleteMapping(value = {"/comment/{commentId}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId) {
        commentCrud.delete(commentId);
    }

}
package portal.education.Monolit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.dto.ViewDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.Category;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.comment.Comment;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.CategoryRepository;
import portal.education.Monolit.data.repos.article.ArticleRepository;
import portal.education.Monolit.data.repos.person.AuthorRepository;
import portal.education.Monolit.service.CategoryCrudService;
import portal.education.Monolit.service.CommentCrudService;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.service.person.AuthorCrudService;
import portal.education.Monolit.service.person.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController()
@RequestMapping("/free/user")
@Tag(name = "Free Usertest's actions", description = "All methods do not need any tokens. Многие URI например getArticlesTopByRating имеет два важных параметра numberPage и sizePage" +
        "Это называется пагинацией(Рашан инглиш). Как это работает: Преставь, что всё, что БД это большая книга с отсортированными запросами и делая запрос к этой книги ты можешь указать номер страницы" +
        ", которую ты хочешь получить и кол-во слов на этой странице(sizePage) т.о. Если тебе нужен первый десяток топовых запросов, то ты казываешь  sizePage = 10 и numberPage =1, второй десяток numberPage =2 соответственно.")
public class FreeUserController {

    @Autowired
    ArticleCrudService articleCrud;

    @Autowired
    private FileController fileController;

    @CrossOrigin
    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok().body("hello");
    }

    @Operation(summary = "Put a view in article. Вызывать, когда пользователь поставил лайк или после просмотра статьи, когда переключился на другую")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 successful operation"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "500")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = {"/article/view"})
    public void putView(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                        @RequestBody ViewDto viewDto) {

        Optional.ofNullable(user)
                .ifPresent(usr -> viewDto.setNickName(usr.getNickname()));
        articleCrud.addView(viewDto);
    }

    @Operation(summary = "Используется для загрузки любых файлов из статей по их id. Этот URI используется редактором для загрузок изображений.")
    @GetMapping(value = "/downloadArticleFile/{id}")
    public ResponseEntity<byte[]> getArticleFile(@Parameter(hidden = true) @PathVariable UUID id) {
        return fileController.getDownloadArticleFile(id);
    }
}

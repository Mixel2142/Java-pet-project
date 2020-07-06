package portal.education.Monolit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.CommentDto;
import portal.education.Monolit.data.dto.RequestLikeDto;
import portal.education.Monolit.data.dto.ResponseLikeDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.comment.Comment;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.CategoryRepository;
import portal.education.Monolit.data.repos.article.ArticleRepository;
import portal.education.Monolit.data.repos.person.AuthorRepository;
import portal.education.Monolit.service.CommentCrudService;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.utils.JwtUtilForMonolit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;


@RestController()
@RequestMapping("/user")
@Tag(name = "Usertest's actions", description = "All methods need access tokens.")
public class UserController {

    @Autowired
    ArticleCrudService articleCrud;

    @Autowired
    CommentCrudService commentCrud;

    @Autowired
    ArticleRepository articleDao;

    @Autowired
    FileController fileController;

    @Autowired
    AuthorRepository authorDao;

    @Autowired
    CategoryRepository categoryDao;


    @Autowired
    JwtUtilForMonolit tokenUtil;


    @Operation(summary = "Put comment in article by article's articleId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "user or articles not found see the error message"),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message."),
    })
    @JsonView(JsonViews.Public.class)
    @PutMapping(value = {"/comment/article"})
    @ResponseStatus(HttpStatus.OK)
    public Comment putComment(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                              @RequestBody @Valid CommentDto comment) {
        return commentCrud.put(user, comment);
    }

    @Operation(summary = "Поставить/снять лайк комменту. Возвращает актуальное кол-во лайков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ResponseLikeDto.class
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message."),
    })
    @PostMapping("/article/like/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLikeDto postLikeToArticle(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                             @Parameter(hidden = true) @PathVariable UUID articleId,
                                             @RequestBody RequestLikeDto like) {

        return new ResponseLikeDto(articleCrud.doLike(user, articleId, Boolean.valueOf(like.isLike())));
    }


    @Operation(summary = "Поставить/снять лайк комменту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ResponseLikeDto.class
                            ))),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message."),
    })
    @PostMapping("/comment/like/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLikeDto postLikeToComment(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                             @PathVariable Long commentId,
                                             @RequestParam RequestLikeDto like) {
        return new ResponseLikeDto(commentCrud.doLike(user, commentId, like.isLike()));
    }

    @Operation(summary = "Сохраняет аватар пользователя")
    @PostMapping(value = "/avatarPhoto/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void postUploadUserFile(@Parameter(hidden = true) @AuthenticationPrincipal User user,
                                   @RequestPart MultipartFile upload) {
        fileController.postUploadUserFile(user, upload);
    }
}

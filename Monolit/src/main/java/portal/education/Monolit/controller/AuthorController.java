package portal.education.Monolit.controller;

import com.fasterxml.jackson.annotation.JsonView;
import portal.education.Monolit.data.dto.ArticleDto;
import portal.education.Monolit.data.dto.ArticleStatusDto;
import portal.education.Monolit.data.dto.ItemsDto;
import portal.education.Monolit.data.jsonview.JsonViews;
import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleStatus;
import portal.education.Monolit.data.model.person.Author;
import portal.education.Monolit.service.article.ArticleCrudService;
import portal.education.Monolit.service.person.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/author")
@Tag(name = "Author's actions", description = "All methods need access token.")
@JsonView({JsonViews.Public.class})
public class AuthorController {

    @Autowired
    private ArticleCrudService articleCrud;

    @Autowired
    private FileController fileController;

    @Autowired
    private AuthorService authorService;

    @Operation(summary = "Delete article by articleId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "id is empty!"),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message."),
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = {"/article/{articleId}"})
    public void deleteArticle(@Parameter(hidden = true) @AuthenticationPrincipal Author author,
                              @PathVariable UUID articleId) {
        authorService.deleteArticle(author, articleId);
    }


    @Operation(summary = "Отправить статью на проверку")
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
    public Article patchArticleStatus(@Parameter(hidden = true) @AuthenticationPrincipal Author author,
                                      @PathVariable UUID articleId, @RequestParam ArticleStatus status) {
        return authorService.patchStatus(author, articleId, status);
    }

    @Operation(summary = "Сохраняет статья в БД", description = " Для изменения статьи использовать PATCH. Cтатусы статьи:" +
            "    |\"Published\"|\n" +
            "    \"Awaiting publication\"|\n" +
            "    \"Publication denied\"|\n" +
            "    \"In developing\"|")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation CREATED"),
            @ApiResponse(responseCode = "404", description = "Одна из картинок не найдена в БД возможно пользователь слишком долго редактировал статью без сохранения. В Body содержится список не найденных картинок!"),
            @ApiResponse(responseCode = "500", description = "Any other exceptions. See error message."),
    })

    @PutMapping("/article")
    public Article putArticle(@Parameter(hidden = true) @AuthenticationPrincipal Author author,
                              @RequestBody @Valid ArticleDto articleDto) {
        articleDto.setAuthorId(author.getId());
        return articleCrud.put(articleDto);
    }


    @Operation(summary = "Возвращает статьи автора по фильтру")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation(send new pair)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Article.class
                            )))
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/article")
    @JsonView({JsonViews.Short.class})
    public ItemsDto<Article> getArticle(@Parameter(hidden = true) @AuthenticationPrincipal Author author,
                                        @RequestBody ArticleStatusDto status) {
        return authorService.findByAuthorAndStatus(author, status);
    }

    @Operation(summary = "Save any file and return URI. Для редактора")
    @PostMapping(value = "/article/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> postUploadArticleFile(@RequestPart MultipartFile upload) {
        return fileController.postUploadArticleFile(upload, "/api/free/user/downloadArticleFile/");
    }
}

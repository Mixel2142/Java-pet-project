package portal.education.FileStorage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import portal.education.FileStorage.dto.UrlForDownloadDto;
import portal.education.FileStorage.service.FileStorageService;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Tag(name = "File Storage Reactive Service", description = "It keeps files for articles and users")
@RestController
@RequestMapping("/storage")
public class FileStorageController {

    //TODO 1) Подключить свагер +
    // 2) Написать скедулер -

    @Autowired
    FileStorageService service;


    @Operation(summary = "Скачать любой файл по ID", description = "Используется для файлов статей и пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Если тело ответа не пусто, то successful operation"),
            @ApiResponse(responseCode = "Another", description = "Error"),
    }
    )
    @GetMapping("/file")
    private Mono<byte[]> getFileById(@RequestParam UUID fileId) {
        return service.loadFileAsByteArray(fileId);
    }


    @Operation(summary = "Загрузить файл для статьи", description = "Используется для файлов статей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Если тело ответа не пусто, то successful operation"),
            @ApiResponse(responseCode = "Another", description = "Error"),
    }
    )
    @PostMapping(value = "/fileForArticle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private Mono<UrlForDownloadDto> saveFileForArticle(@RequestPart FilePart upload) {
       return service.saveFileForArticle(upload);
    }


    @Operation(summary = "Загрузить любой файл", description = "Используется для файлов пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Если тело ответа не пусто, то successful operation"),
            @ApiResponse(responseCode = "Another", description = "Error"),
    }
    )
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private Mono<UrlForDownloadDto> saveFile(@RequestPart FilePart upload) {
       return service.saveFile(upload);
    }

    @Operation(summary = "Удалить любой файл", description = "Используется системой")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "Another", description = "Error"),
    }
    )
    @DeleteMapping("/file")
    private Mono<Void> saveAvatar(@RequestPart UUID fileId) {
        return service.deleteById(fileId);
    }

}

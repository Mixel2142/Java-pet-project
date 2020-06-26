package portal.education.Monolit.controller;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.service.FileStorageService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/file")
@Tag(name = "Files save/download", description = "Need tokens, but free user?")
@Hidden
public class FileController {

    @Autowired
    private FileStorageService fileStorage;

    @PostMapping(value = "/uploadArticleFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postUploadArticleFile(@RequestPart MultipartFile upload, String uri) {

        String idLong = fileStorage.storeArticleFile(upload).toString();

        return ResponseEntity.ok()
                .body(Map.of("url", uri + idLong));
    }

    @GetMapping(value = "/downloadArticleFile/{id}")
    public ResponseEntity<byte[]> getDownloadArticleFile(@PathVariable UUID id) {
        return ResponseEntity.ok().body(fileStorage.loadAsByteArrayArticleFile(id));
    }

    @PostMapping(value = "/uploadUserFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void postUploadUserFile(User user, @RequestPart MultipartFile upload) {
        fileStorage.storeUserFile(user, upload).toString();
    }

}

package portal.education.Monolit.service;

import portal.education.Monolit.data.model.person.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public interface FileStorageService {

    UUID storeArticleFile(MultipartFile file);

    byte[] loadAsByteArrayArticleFile(UUID id);

    void deleteByIdArticleFile(UUID id);

    UUID storeUserFile(User user, MultipartFile file);

    byte[] loadAsByteArrayUserFile(UUID id);

    void deleteByIdUserFile(UUID id);

}

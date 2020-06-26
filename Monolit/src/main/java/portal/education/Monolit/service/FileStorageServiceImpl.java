package portal.education.Monolit.service;

import portal.education.Monolit.data.model.article.ArticleFile;
import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.model.person.UserFile;
import portal.education.Monolit.data.repos.article.ArticleFileRepository;
import portal.education.Monolit.data.repos.person.UserFilesRepository;
import portal.education.Monolit.utils.ExcMsg;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.rowset.serial.SerialBlob;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@Service
@NoArgsConstructor
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

    private Long timeLifeFileWithoutArticle;

    @Value("${time.life.file.without.article}")
    public void setTimeLifeFileWithoutArticle(Long timeLifeFileWithoutArticle) {
        this.timeLifeFileWithoutArticle = timeLifeFileWithoutArticle * 1000;
    }

    @Autowired
    private ArticleFileRepository articleFilesDao;

    @Autowired
    private UserFilesRepository userFilesDao;

    @Override
    public UUID storeArticleFile(MultipartFile mulFile) {
        try {
            Date expiryDate = Date.from(Instant.now().plusMillis(timeLifeFileWithoutArticle));

            ArticleFile file = new ArticleFile(
                    mulFile.getContentType(),
                    mulFile.getSize(),
                    new SerialBlob(mulFile.getBytes()),
                    expiryDate
            );

            articleFilesDao.save(file);

            return file.getId();
        } catch (IOException | SQLException ex) {
            log.error(ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] loadAsByteArrayArticleFile(UUID id) {
        try {
            return StreamUtils.copyToByteArray(
                    articleFilesDao.findById(id)
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    ExcMsg.FileNotFound(id)
                            ))
                            .getData()
                            .getBinaryStream()
            );
        } catch (IOException | SQLException ex) {
            log.error(ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "SQL error"
            );
        }
    }

    @Override
    public void deleteByIdArticleFile(UUID id) {
        try {
            articleFilesDao.deleteById(id);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @Override
    public UUID storeUserFile(User user, MultipartFile mulFile) {
        try {
            Date expiryDate = Date.from(Instant.now().plusMillis(timeLifeFileWithoutArticle));

            UserFile file = new UserFile(
                    mulFile.getContentType(),
                    mulFile.getSize(),
                    new SerialBlob(mulFile.getBytes()),
                    user
            );

            userFilesDao.save(file);

            return file.getId();
        } catch (IOException | SQLException ex) {
            log.error(ex);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public byte[] loadAsByteArrayUserFile(UUID id) {
        try {
            return StreamUtils.copyToByteArray(
                    userFilesDao.findById(id)
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    ExcMsg.FileNotFound(id)
                            ))
                            .getData()
                            .getBinaryStream()
            );
        } catch (IOException | SQLException ex) {
            log.error(ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "SQL error"
            );
        }
    }

    @Override
    public void deleteByIdUserFile(UUID id) {
        try {
            userFilesDao.deleteById(id);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

}

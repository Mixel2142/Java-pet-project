package portal.education.Monolit.service.article;

import portal.education.Monolit.data.model.article.ArticleFile;
import portal.education.Monolit.data.repos.article.ArticleFileRepository;
import portal.education.Monolit.utils.ExcMsg;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@NoArgsConstructor
@Transactional
public class ArticleFileCrudServiceImpl implements ArticleFileCrudService {
    @Autowired
    ArticleFileRepository articleFilesDao;


    @Override
    public ArticleFile getById(UUID id) {
        return articleFilesDao.findById(id)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            ExcMsg.FileNotFound(id));
                });
    }
}

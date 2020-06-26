package portal.education.Monolit.service.article;

import portal.education.Monolit.data.model.article.Article;
import portal.education.Monolit.data.model.article.ArticleFile;

import java.util.UUID;

public interface ArticleFileCrudService {

    ArticleFile getById(UUID id);
}

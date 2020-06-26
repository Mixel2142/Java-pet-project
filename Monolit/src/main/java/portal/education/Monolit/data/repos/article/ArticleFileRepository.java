package portal.education.Monolit.data.repos.article;


import portal.education.Monolit.data.model.article.ArticleFile;
import portal.education.Monolit.data.repos.abstractRepos.AbstractFileRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface ArticleFileRepository extends AbstractFileRepository<ArticleFile> {

    @Modifying
    @Query("DELETE FROM ArticleFile img WHERE img.expiryDate <= :date AND img.article = NULL")
    void deleteAllExpiredSince(@Param("date") Date date);

    /*    @Modifying
    @Query("UPDATE ArticleFile img SET img.article.id = :articleId WHERE img.id = :fileId")
    void bindArticleAndFile(@Param("articleId") UUID articleId, @Param("fileId") Long fileId); */

}

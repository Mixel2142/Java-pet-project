package portal.education.Monolit.service.graphql.datafetcher;

import portal.education.Monolit.data.repos.article.ArticleRepository;
import portal.education.Monolit.data.repos.comment.CommentRepository;
import portal.education.Monolit.service.graphql.DataFetcherUtil;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class CommentDataFetcher {

    @Autowired
    CommentRepository commentDao;

    public DataFetcher getCommentsByArticleId() {
        return dataFetchingEnvironment -> {
            UUID articleId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);

            return commentDao.findByArticleId(articleId, pageable);
        };
    }
}

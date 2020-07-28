//package portal.education.GraphQLR.service.graphql.datafetcher;
//
//import graphql.schema.DataFetcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//import portal.education.GraphQLR.service.graphql.DataFetcherUtil;
//import portal.education.Monolit.data.repos.comment.CommentRepository;
//
//import java.util.UUID;
//
//
//@Component
//public class CommentDataFetcher {
//
//    @Autowired
//    CommentRepository commentDao;
//
//    public DataFetcher getCommentsByArticleId() {
//        return dataFetchingEnvironment -> {
//            UUID articleId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
//            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);
//
//            return commentDao.findByArticleId(articleId, pageable);
//        };
//    }
//}

//package portal.education.GraphQLR.service.graphql.datafetcher;
//
//import graphql.schema.DataFetcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//import portal.education.GraphQLR.service.graphql.DataFetcherUtil;
//import portal.education.Monolit.data.repos.article.ArticleRepository;
//
//import java.util.UUID;
//
//
//@Component
//public class ArticleDataFetcher {
//
//    @Autowired
//    ArticleRepository articleDao;
//
//    public DataFetcher getArticleByIdDataFetcher() {
//        return dataFetchingEnvironment -> {
//            UUID articleId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
//            return articleDao.getOne(articleId);
//        };
//    }
//
//    public DataFetcher getArticlesDataFetcher() {
//        return dataFetchingEnvironment -> {
//            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);
//
//            return articleDao.findAll(pageable);
//        };
//    }
//
//
//}

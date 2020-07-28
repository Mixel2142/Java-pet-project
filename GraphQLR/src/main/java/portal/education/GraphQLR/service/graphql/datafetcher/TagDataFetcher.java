//package portal.education.GraphQLR.service.graphql.datafetcher;
//
//import graphql.schema.DataFetcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//import portal.education.GraphQLR.service.graphql.DataFetcherUtil;
//import portal.education.Monolit.data.repos.article.TagRepository;
//
//
//import java.util.Optional;
//
//
//@Component
//public class TagDataFetcher {
//
//    @Autowired
//    TagRepository tagDao;
//
//    public DataFetcher getTags() {
//        return dataFetchingEnvironment -> {
//
//            String name = Optional.ofNullable((String) dataFetchingEnvironment.getArgument("name"))
//                    .orElse("");
//
//            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);
//
//            return tagDao.findByNameStartingWith(name, pageable);
//        };
//    }
//}

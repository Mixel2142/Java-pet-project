//package portal.education.GraphQLR.service.graphql.datafetcher;
//
//import graphql.schema.DataFetcher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//import portal.education.GraphQLR.service.graphql.DataFetcherUtil;
//import portal.education.Monolit.data.repos.CategoryRepository;
//
//import java.util.Optional;
//
//
//@Component
//public class CategoryDataFetcher {
//
//    @Autowired
//    CategoryRepository categoryDao;
//
//    public DataFetcher getCategories() {
//        return dataFetchingEnvironment -> {
//
//            String name = Optional.ofNullable((String) dataFetchingEnvironment.getArgument("name"))
//                    .orElse("");
//
//            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);
//
//            return categoryDao.findByNameStartingWith(name, pageable);
//        };
//    }
//}

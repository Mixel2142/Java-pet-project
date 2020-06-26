package portal.education.Monolit.service.graphql.datafetcher;

import portal.education.Monolit.data.repos.CategoryRepository;
import portal.education.Monolit.data.repos.comment.CommentRepository;
import portal.education.Monolit.service.graphql.DataFetcherUtil;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class CategoryDataFetcher {

    @Autowired
    CategoryRepository categoryDao;

    public DataFetcher getCategories() {
        return dataFetchingEnvironment -> {

            String name = Optional.ofNullable((String) dataFetchingEnvironment.getArgument("name"))
                    .orElse("");

            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);

            return categoryDao.findByNameStartingWith(name, pageable);
        };
    }
}

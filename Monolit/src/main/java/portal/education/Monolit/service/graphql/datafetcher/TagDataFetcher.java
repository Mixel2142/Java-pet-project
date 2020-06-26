package portal.education.Monolit.service.graphql.datafetcher;

import portal.education.Monolit.data.model.Tag;
import portal.education.Monolit.data.repos.CategoryRepository;
import portal.education.Monolit.data.repos.article.TagRepository;
import portal.education.Monolit.service.graphql.DataFetcherUtil;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class TagDataFetcher {

    @Autowired
    TagRepository tagDao;

    public DataFetcher getTags() {
        return dataFetchingEnvironment -> {

            String name = Optional.ofNullable((String) dataFetchingEnvironment.getArgument("name"))
                    .orElse("");

            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);
            
            return tagDao.findByNameStartingWith(name, pageable);
        };
    }
}

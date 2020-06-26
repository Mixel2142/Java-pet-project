package portal.education.Monolit.service.graphql.datafetcher;

import antlr.ASdebug.ASDebugStream;
import portal.education.Monolit.data.repos.person.AuthorRepository;
import portal.education.Monolit.service.graphql.DataFetcherUtil;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class AuthorDataFetcher {

    @Autowired
    AuthorRepository authorDao;

    public DataFetcher getAuthorByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            UUID authorId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
            return authorDao.getOne(authorId);
        };
    }

    public DataFetcher getAuthorsDataFetcher() {
        return dataFetchingEnvironment -> {
            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);

            return authorDao.findAll(pageable);
        };
    }


}

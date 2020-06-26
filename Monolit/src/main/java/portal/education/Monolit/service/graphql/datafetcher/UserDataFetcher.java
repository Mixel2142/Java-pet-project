package portal.education.Monolit.service.graphql.datafetcher;

import portal.education.Monolit.data.model.person.User;
import portal.education.Monolit.data.repos.person.UserRepository;
import portal.education.Monolit.service.graphql.DataFetcherUtil;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class UserDataFetcher {

    @Autowired
    UserRepository<User> UserDao;

    public DataFetcher getUserByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            UUID userId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
            return UserDao.getOne(userId);
        };
    }

    public DataFetcher getUsersDataFetcher() {
        return dataFetchingEnvironment -> {
            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);

            return UserDao.findAll(pageable);
        };
    }


}

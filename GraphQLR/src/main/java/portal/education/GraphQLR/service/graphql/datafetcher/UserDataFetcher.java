package portal.education.GraphQLR.service.graphql.datafetcher;

import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import portal.education.GraphQLR.entity.User;
import portal.education.GraphQLR.repository.UserRepository;
import portal.education.GraphQLR.service.graphql.DataFetcherUtil;


import java.util.UUID;


@Component
public class UserDataFetcher {

    @Autowired
    DatabaseClient client;

    @Autowired
    UserRepository userRepository;

    public DataFetcher getUserByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            UUID userId = UUID.fromString(dataFetchingEnvironment.getArgument("id"));
            return userRepository.findById(userId);
        };
    }

    public DataFetcher getUsersDataFetcher() {
        return dataFetchingEnvironment -> {
            Pageable pageable = DataFetcherUtil.buildSort(dataFetchingEnvironment);

            return client.select()
                    .from(User.class)
                    .page(pageable)
                    .fetch()
                    .all();
        };
    }

}

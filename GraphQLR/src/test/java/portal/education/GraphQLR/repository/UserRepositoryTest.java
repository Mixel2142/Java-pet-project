package portal.education.GraphQLR.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import portal.education.GraphQLR.entity.User;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DataR2dbcTest
class UserRepositoryTest {

    @Autowired
    DatabaseClient client;

    @Test
    public void testDatabaseClientExisted() {
        assertNotNull(client);
    }

    @Test
    public void findAllSortedTest() {
        System.out.println(this.client.select().from(User.class).page( PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC,"nickname"))).fetch().all().collect(Collectors.toList()).block());
    }

}
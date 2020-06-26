package portal.education.Monolit.service.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import portal.education.Monolit.service.graphql.datafetcher.*;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

@Component
public class GraphQLProvider {

    @Autowired
    private ArticleDataFetcher articleDataFetcher;

    @Autowired
    private CommentDataFetcher commentDataFetcher;

    @Autowired
    private CategoryDataFetcher categoryDataFetcher;

    @Autowired
    private AuthorDataFetcher authorDataFetcher;

    @Autowired
    private TagDataFetcher tagDataFetcher;

    @Autowired
    private UserDataFetcher userDataFetcher;

    private GraphQL graphQL;

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("graphql/schema.graphql");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("getArticleById", articleDataFetcher.getArticleByIdDataFetcher())
                        .dataFetcher("getArticles", articleDataFetcher.getArticlesDataFetcher())

                        .dataFetcher("getCommentsByArticleId", commentDataFetcher.getCommentsByArticleId())

                        .dataFetcher("getCategories", categoryDataFetcher.getCategories())

                        .dataFetcher("getTags", tagDataFetcher.getTags())

                        .dataFetcher("getAuthorById", authorDataFetcher.getAuthorByIdDataFetcher())
                        .dataFetcher("getAuthors", authorDataFetcher.getAuthorsDataFetcher())

                        .dataFetcher("getUserById", userDataFetcher.getUserByIdDataFetcher())
                        .dataFetcher("getUsers", userDataFetcher.getUsersDataFetcher())

                )
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }
}

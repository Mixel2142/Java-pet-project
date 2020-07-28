package portal.education.GraphQLR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(scanBasePackages = "portal.education.GraphQLR.repository")
@EnableR2dbcRepositories
//@EnableEurekaClient
@EnableWebFlux
public class GraphQlrApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphQlrApplication.class, args);

	}

}

package portal.education.Monolit;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//@EnableEurekaClient
@Log4j2(topic = "ASYNC__JSON__FILE__APPENDER")
public class MonolitApplication {

	static String port;

	static String swaggerUrl;

	static String graphiqlUrl;

	@Value("${graphiql.mapping}")
	public void setGraphiqlUrl(String graphiqlUrl) {
		this.graphiqlUrl = graphiqlUrl;
	}

	@Value("${server.port}")
	public void setPort(String port) {
		this.port = port;
	}

	@Value("${springdoc.swagger-ui.path}")
	public void setSwaggerUrl(String swaggerUrl) {
		this.swaggerUrl = swaggerUrl;
	}

	public static void main(String[] args) {
		SpringApplication.run(MonolitApplication.class, args);
		log.debug("\nНе забудь! Весь API можно просмотреть по ссылке: http://localhost:" + port + swaggerUrl);
		log.debug("\nНе забудь! GraphiQl можно просмотреть по ссылке: http://localhost:" + port + graphiqlUrl);

	}

}

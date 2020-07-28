package portal.education.NotificationResolver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableEurekaClient
public class NotificationResolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationResolverApplication.class, args);
	}

}

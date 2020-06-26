package portal.education.Monolit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;


@ActiveProfiles("test")
@SpringBootTest
class MonolitApplicationTests {

	@Test
	void contextLoads() throws InterruptedException {
		TimeUnit.SECONDS.sleep(6);
	}

}

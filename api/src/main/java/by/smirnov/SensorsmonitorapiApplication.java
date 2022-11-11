package by.smirnov;

import by.smirnov.config.PersistenceProvidersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "by.smirnov")
@Import({PersistenceProvidersConfiguration.class})
public class SensorsmonitorapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorsmonitorapiApplication.class, args);
	}

}

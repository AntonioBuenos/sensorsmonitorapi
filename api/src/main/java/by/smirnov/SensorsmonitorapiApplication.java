package by.smirnov;

import by.smirnov.config.PersistenceProvidersConfiguration;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication(scanBasePackages = "by.smirnov")
@Import({PersistenceProvidersConfiguration.class})
@SecurityScheme(name = "JWT Bearer",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT",
		description = "Bearer token for the project.")
public class SensorsmonitorapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorsmonitorapiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true)
				.setFieldAccessLevel(PRIVATE)
		;
		return mapper;
	}
}

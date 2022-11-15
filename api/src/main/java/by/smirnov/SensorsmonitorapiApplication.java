package by.smirnov;

import by.smirnov.config.PersistenceProvidersConfiguration;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication(scanBasePackages = "by.smirnov")
@Import({PersistenceProvidersConfiguration.class})
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

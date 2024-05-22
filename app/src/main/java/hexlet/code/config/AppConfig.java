package hexlet.code.config;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    public Faker getFaker() {
        return new Faker(new Locale("en", "US"));
    }
}
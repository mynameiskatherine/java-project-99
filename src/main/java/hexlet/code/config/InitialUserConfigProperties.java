package hexlet.code.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@Setter
@Getter
public class InitialUserConfigProperties {
    private String email;
    private String password;
}

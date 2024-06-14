package hexlet.code.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "initial")
@Setter
@Getter
public class InitialDataConfigProperties {
    private List<String> labels = new ArrayList<>();
    private List<String> taskStatuses = new ArrayList<>();
}

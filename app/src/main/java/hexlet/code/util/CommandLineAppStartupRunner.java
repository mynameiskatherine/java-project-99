package hexlet.code.util;

import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.email}")
    private String adminEmail;

    @Value("${user.password}")
    private String adminPassword;

    @Value("${task-statuses}")
    private List<String> taskStatuses;

    @Override
    public void run(String...args) throws Exception {
        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setCreatedAt(LocalDateTime.now());
        if (!userRepository.existsByEmail(adminEmail)) {
            userRepository.save(admin);
        }

        taskStatuses.stream()
                .map(ts -> {
                    TaskStatus taskStatus = new TaskStatus();
                    taskStatus.setSlug(ts);
                    String taskName = Arrays.stream(ts.split("_"))
                            .map(w -> StringUtils.capitalize(w))
                            .collect(Collectors.joining());
                    taskStatus.setName(taskName);
                    return taskStatus;
                })
                .forEach(ts -> {
                    if (!taskStatusRepository.existsBySlug(ts.getSlug())) {
                        taskStatusRepository.save(ts);
                    }
                });
    }
}

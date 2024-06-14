package hexlet.code.util;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private LabelRepository labelRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.email}")
    private String userEmail;

    @Value("${user.password}")
    private String userPassword;

    @Value("${task-statuses}")
    private List<String> taskStatuses = new ArrayList<>();

    @Value("${labels}")
    private List<String> labels = new ArrayList<>();

    @Override
    public void run(String...args) {

        User user = new User(userEmail, passwordEncoder.encode(userPassword));
        userRepository.save(user);

        taskStatuses.stream()
                .map(ts -> {
                    TaskStatus taskStatus = new TaskStatus();
                    taskStatus.setSlug(ts);
                    String taskStatusName = Arrays.stream(ts.split("_"))
                            .map(w -> StringUtils.capitalize(w))
                            .collect(Collectors.joining());
                    taskStatus.setName(taskStatusName);
                    return taskStatus;
                })
                .forEach(ts -> {
                    if (!taskStatusRepository.existsBySlug(ts.getSlug())) {
                        taskStatusRepository.save(ts);
                    }
                });

        labels.stream()
                .forEach(l -> {
                    Label label = new Label();
                    label.setName(l);
                    if (!labelRepository.existsByName(l)) {
                        labelRepository.save(label);
                    }
                });
    }
}

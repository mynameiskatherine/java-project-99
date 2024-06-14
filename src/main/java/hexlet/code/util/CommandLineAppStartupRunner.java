package hexlet.code.util;

import hexlet.code.config.InitialDataConfigProperties;
import hexlet.code.config.InitialUserConfigProperties;
import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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
    @Autowired
    private InitialUserConfigProperties userConfigProperties;
    @Autowired
    private InitialDataConfigProperties dataConfigProperties;

    @Override
    public void run(String...args) {

        User user = new User(userConfigProperties.getEmail(),
                passwordEncoder.encode(userConfigProperties.getPassword()));
        userRepository.save(user);

        dataConfigProperties.getTaskStatuses().stream()
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

        dataConfigProperties.getLabels().stream()
                .forEach(l -> {
                    Label label = new Label();
                    label.setName(l);
                    if (!labelRepository.existsByName(l)) {
                        labelRepository.save(label);
                    }
                });
    }
}

package hexlet.code.util;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


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

    @Override
    public void run(String...args) {

        User user = new User("hexlet@example.com",
                passwordEncoder.encode("qwerty"));
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        } else {
            User existedAdmin = userRepository.findByEmail(user.getEmail()).get();
            if (!(existedAdmin.getPassword().equals(user.getPassword()))) {
                existedAdmin.setPassword(user.getPassword());
                userRepository.save(existedAdmin);
            }
        }

        User user2 = new User("test@test.com",
                passwordEncoder.encode("testtest"));
        if (!userRepository.existsByEmail(user2.getEmail())) {
            userRepository.save(user2);
        } else {
            User existedAdmin = userRepository.findByEmail(user2.getEmail()).get();
            if (!(existedAdmin.getPassword().equals(user2.getPassword()))) {
                existedAdmin.setPassword(user2.getPassword());
                userRepository.save(existedAdmin);
            }
        }

        taskStatusRepository.save(new TaskStatus("draft", "draft"));
        taskStatusRepository.save(new TaskStatus("to_review", "ToReview"));
        taskStatusRepository.save(new TaskStatus("to_be_fixed", "ToBeFixed"));
        taskStatusRepository.save(new TaskStatus("to_publish", "ToPublish"));
        taskStatusRepository.save(new TaskStatus("published", "published"));

        labelRepository.save(new Label("bug"));
        labelRepository.save(new Label("feature"));
    }
}

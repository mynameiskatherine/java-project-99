package hexlet.code.util;

import hexlet.code.model.Task;
import hexlet.code.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;

    private Model<Task> taskModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
                .supply(Select.field(User::getLastName), () -> faker.name().lastName())
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.internet().password(3, 20))
                .ignore(Select.field(User::getTasks))
                .toModel();

        taskModel = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getName), () -> faker.lorem().sentence(3))
                .supply(Select.field(Task::getDescription), () -> faker.lorem().sentence(10))
                .supply(Select.field(Task::getIndex), () -> faker.number().numberBetween(1L, 10000000L))
                .ignore(Select.field(Task::getUser))
                .ignore(Select.field(Task::getTaskStatus))
                .ignore(Select.field(Task::getLabels))
                .toModel();

    }
}

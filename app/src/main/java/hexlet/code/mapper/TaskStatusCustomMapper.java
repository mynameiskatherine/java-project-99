package hexlet.code.mapper;

import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusCustomMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    public TaskStatus toTaskStatus(String slug) {
        if (slug != null && taskStatusRepository.existsBySlug(slug)) {
            return taskStatusRepository.findBySlug(slug).get();
        } else {
            throw new RuntimeException("No such task status exists");
        }
    }
}

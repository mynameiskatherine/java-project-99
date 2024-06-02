package hexlet.code.mapper;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomMapper {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;

    public TaskStatus toTaskStatus(String slug) {
        if (slug != null && taskStatusRepository.existsBySlug(slug)) {
            return taskStatusRepository.findBySlug(slug).get();
        } else {
            throw new RuntimeException("No such task status exists");
        }
    }

    public List<Label> toLabel(List<Long> labelsIds) {
        return labelsIds.stream()
                .map(id -> labelRepository.findById(id).get())
                .toList();
    }

    public List<Long> toLabelIds(List<Label> labels) {
        return labels.stream()
                .map(label -> label.getId())
                .toList();
    }

    public List<String> toLabelNames(List<Label> labels) {
        return labels.stream()
                .map(label -> label.getName())
                .toList();
    }

}

package hexlet.code.service;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.mapper.JsonNullableMapper;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private JsonNullableMapper jsonNullableMapper;

    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDTO> getAll() {
        List<TaskDTO> taskList = taskRepository.findAll().stream().map(taskMapper::map).toList();
        return taskList;
    }

    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id %d not found".formatted(id)));
        return taskMapper.map(task);
    }

    public TaskDTO create(TaskCreateDTO taskCreateDTO) {
        Task task = taskMapper.create(taskCreateDTO);
        taskRepository.save(task);
        TaskStatus taskStatus = task.getTaskStatus();
        taskStatus.addTask(task);
        taskStatusRepository.save(taskStatus);
        User user = task.getUser();
        if (user != null) {
            user = userRepository.findById(user.getId()).get();
            user.addTask(task);
            userRepository.save(user);
        }
        return taskMapper.map(task);
    }

//    public TaskDTO create(TaskCreateDTO taskCreateDTO) {
//        Task task = taskMapper.create(taskCreateDTO);
//        System.out.println(task.toString());
//        TaskStatus taskStatus = task.getTaskStatus();
//        System.out.println(taskStatus.toString());
//        User user = task.getUser();
//        System.out.println(user.toString());
//        taskRepository.save(task);
//        taskStatus.addTask(task);
//        if (user != null) {
//            user = userRepository.findById(user.getId()).get();
//            user.addTask(task);
//            userRepository.save(user);
//        }
//        return taskMapper.map(task);
//    }


    public TaskDTO update(Long id, TaskUpdateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id %d not found".formatted(id)));
        final User oldUser = task.getUser();
        final TaskStatus oldTaskStatus = task.getTaskStatus();

        if (jsonNullableMapper.isPresent(taskUpdateDTO.getAssigneeId())
                && (taskUpdateDTO.getAssigneeId().get() != oldUser.getId())) {
            oldUser.removeTask(task);
            userRepository.save(oldUser);
        }
        if (jsonNullableMapper.isPresent(taskUpdateDTO.getStatus()) &&
                !(taskUpdateDTO.getStatus().get().equals(oldTaskStatus.getSlug()))) {
            oldTaskStatus.removeTask(task);
            taskStatusRepository.save(oldTaskStatus);
        }

        taskMapper.update(taskUpdateDTO, task);

        if (!task.getUser().equals(oldUser)) {
            User newUser = task.getUser();
            newUser.addTask(task);
            userRepository.save(newUser);
        }
        if (!task.getTaskStatus().equals(oldTaskStatus)) {
            TaskStatus newTaskStatus = task.getTaskStatus();
            newTaskStatus.addTask(task);
            taskStatusRepository.save(newTaskStatus);
        }

        taskRepository.save(task);
        return taskMapper.map(task);
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id %d not found".formatted(id)));
        User user = task.getUser();
        user.removeTask(task);
        userRepository.save(user);
        TaskStatus taskStatus = task.getTaskStatus();
        taskStatus.removeTask(task);
        taskStatusRepository.save(taskStatus);
        taskRepository.deleteById(id);
    }
}

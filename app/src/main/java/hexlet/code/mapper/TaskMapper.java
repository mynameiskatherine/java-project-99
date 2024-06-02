package hexlet.code.mapper;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.model.Task;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper (
        uses = { JsonNullableMapper.class, ReferenceMapper.class, CustomMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {
    @Mapping(source = "user.id", target = "assignee_id")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "labels", target = "taskLabelIds")
    public abstract TaskDTO map(Task task);

    @Mapping(source = "user.id", target = "assignee_id")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "labels", target = "taskLabelIds")
    public abstract TaskUpdateDTO mapToUpdate(Task task);

    @Mapping(source = "user.id", target = "assignee_id")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "labels", target = "taskLabelIds")
    public abstract TaskCreateDTO mapToCreate(Task task);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "content", target = "description")
    @Mapping(source = "status", target = "taskStatus")
    @Mapping(source = "assignee_id", target = "user")
    @Mapping(source = "taskLabelIds", target = "labels")
    public abstract Task create(TaskCreateDTO taskCreateDTO);

    @Mapping(source = "title", target = "name")
    @Mapping(source = "content", target = "description")
    @Mapping(source = "status", target = "taskStatus")
    @Mapping(source = "assignee_id", target = "user")
    @Mapping(source = "taskLabelIds", target = "labels")
    public abstract void update(TaskUpdateDTO taskUpdateDTO, @MappingTarget Task model);

}

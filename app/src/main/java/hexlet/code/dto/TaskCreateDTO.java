package hexlet.code.dto;

import hexlet.code.model.Label;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
public class TaskCreateDTO {
    private Long index;
    private Long assignee_id;
    @NotBlank
    @Size(min = 1)
    private String title;
    private String content;
    @NotNull
    private String status;
    private List<Long> taskLabelIds = new ArrayList<>();
}

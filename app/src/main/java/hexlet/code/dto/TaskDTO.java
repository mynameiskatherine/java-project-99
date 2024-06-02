package hexlet.code.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hexlet.code.model.Label;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private Long index;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private Long assignee_id;
    private String title;
    private String content;
    private String status;
    private List<Long> taskLabelIds;
}

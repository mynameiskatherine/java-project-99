package hexlet.code.dto;

import hexlet.code.model.Label;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class TaskUpdateDTO {

    private JsonNullable<Long> index;
    private JsonNullable<Long> assignee_id;
    @NotBlank
    @Size(min = 1)
    private JsonNullable<String> title;
    private JsonNullable<String> content;
    @NotNull
    private JsonNullable<String> status;
    private JsonNullable<List<Long>> taskLabelIds;
}

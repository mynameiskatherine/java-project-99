package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskFilterSearchParameters {
    private String titleCont;
    private Long assigneeId;
    private String status;
    private Long labelId;
}

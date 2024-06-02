package hexlet.code.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabelCreateUpdateDTO {
    @Column(unique = true)
    @Size(min = 3, max = 3000)
    @NotBlank
    private String name;
}

package hexlet.code.controller.api;

import hexlet.code.dto.LabelCreateUpdateDTO;
import hexlet.code.dto.LabelDTO;
import hexlet.code.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelsController {

    @Autowired
    private LabelService labelService;

    @GetMapping("")
    public ResponseEntity<List<LabelDTO>> index() {
        List<LabelDTO> labels = labelService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(labels.size()))
                .body(labels);
    }

    @GetMapping("/{id}")
    public LabelDTO show(@PathVariable Long id) {
        return labelService.findById(id);
    }

    @PostMapping("")
    public LabelDTO create(@Valid @RequestBody LabelCreateUpdateDTO labelCreateDTO) {
        return labelService.create(labelCreateDTO);
    }

    @PutMapping("/{id}")
    public LabelDTO update(@PathVariable Long id, @Valid @RequestBody LabelCreateUpdateDTO labelUpdateDTO) {
        return labelService.update(id, labelUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        labelService.delete(id);
    }
}

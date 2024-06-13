package hexlet.code.service;

import hexlet.code.dto.LabelCreateUpdateDTO;
import hexlet.code.dto.LabelDTO;
import hexlet.code.exception.ResourceIsInUseException;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;

    public List<LabelDTO> getAll() {
        List<LabelDTO> labelList = labelRepository.findAll().stream().map(labelMapper::map).toList();
        return labelList;
    }

    public LabelDTO findById(Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id %d not found".formatted(id)));
        return labelMapper.map(label);
    }

    public LabelDTO create(LabelCreateUpdateDTO labelCreateDTO) {
        Label label = labelMapper.create(labelCreateDTO);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    public LabelDTO update(Long id, LabelCreateUpdateDTO labelUpdateDTO) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id %d not found".formatted(id)));
        labelMapper.update(labelUpdateDTO, label);
        labelRepository.save(label);
        return labelMapper.map(label);
    }

    public void delete(Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id %d not found".formatted(id)));
        try {
            labelRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceIsInUseException("Label with id %d cannot be deleted ".formatted(id)
                    + "due to integrity constraint violation" + Arrays.toString(e.getStackTrace()));
        }
    }
}

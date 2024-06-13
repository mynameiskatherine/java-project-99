package hexlet.code.mapper;

import hexlet.code.dto.LabelCreateUpdateDTO;
import hexlet.code.dto.LabelDTO;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabelMapper {
    public abstract LabelDTO map(Label label);
    public abstract Label create(LabelCreateUpdateDTO labelCreateUpdateDTO);
    public abstract void update(LabelCreateUpdateDTO labelCreateUpdateDTO, @MappingTarget Label model);
}

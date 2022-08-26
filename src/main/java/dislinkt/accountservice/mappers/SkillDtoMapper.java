package dislinkt.accountservice.mappers;

import dislinkt.accountservice.dtos.SkillDto;
import dislinkt.accountservice.entities.Skill;
import dislinkt.accountservice.entities.SkillType;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillDtoMapper {

    public SkillDto toDto(Skill skill) {
        return new SkillDto(skill.getId(), skill.getName(), skill.getType().getValue());
    }

    public Skill toEntity(SkillDto dto) {
        return new Skill(dto.getId(), dto.getName(), SkillType.valueOfInt(dto.getType()));
    }

    public List<SkillDto> toCollectionDto(Collection<Skill> skills) {
        return skills.stream().map(this::toDto).collect(Collectors.toList());
    }

}

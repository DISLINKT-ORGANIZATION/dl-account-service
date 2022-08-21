package dislinkt.accountservice.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import dislinkt.accountservice.entities.Proficiency;
import dislinkt.accountservice.entities.SkillProficiency;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.SkillProficiencyDto;
import dislinkt.accountservice.entities.Skill;
import dislinkt.accountservice.entities.SkillType;

@Service
public class SkillProficiencyDtoMapper {

    public SkillProficiencyDto toDto(SkillProficiency skillProficiency) {
        return new SkillProficiencyDto(
                skillProficiency.getId(),
                skillProficiency.getSkill().getName(),
                skillProficiency.getSkill().getType().getValue(),
                skillProficiency.getProficiency().getValue());
    }

    public SkillProficiency toEntity(SkillProficiencyDto dto) {
        Skill skill = new Skill(dto.getName(), SkillType.valueOfInt(dto.getType()));
        return new SkillProficiency(Proficiency.valueOfInt(dto.getType()), skill);
    }

    public List<SkillProficiencyDto> toCollectionDto(Collection<SkillProficiency> skillProficiencies) {
        return skillProficiencies.stream().map(this::toDto).collect(Collectors.toList());
    }
}

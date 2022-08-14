package dislinkt.accountservice.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.SkillDto;
import dislinkt.accountservice.entities.Skill;
import dislinkt.accountservice.entities.SkillType;

@Service
public class SkillDtoMapper {

	public SkillDto toDto(Skill skill) {
		return new SkillDto(skill.getId(), skill.getName(), skill.getType().ordinal());
	}

	public Skill toEntity(SkillDto dto) {
		return new Skill(dto.getName(), SkillType.valueOfInt(dto.getType()));
	}
	
	public List<SkillDto> toCollectionDto(Collection<Skill> skills) {
		return skills.stream().map(this::toDto).collect(Collectors.toList());
	}
}

package dislinkt.accountservice.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import dislinkt.accountservice.dtos.SkillDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.entities.SeniorityLevel;
import dislinkt.accountservice.entities.WorkingExperience;

@Service
public class WorkingExperienceDtoMapper {

	@Autowired
	private SkillDtoMapper skillMapper;

	public WorkingExperienceDto toDto(WorkingExperience workingExperience) {
		return new WorkingExperienceDto(
				workingExperience.getId(),
				workingExperience.getPosition().getTitle(),
				workingExperience.getPosition().getId(),
				new ArrayList<>(skillMapper.toCollectionDto(workingExperience.getSkills())),
				workingExperience.getSeniority().getValue(),
				workingExperience.getStartDate(),
				workingExperience.getEndDate()

		);
	}

	public List<WorkingExperienceDto> toCollectionDto(Collection<WorkingExperience> workingExperience) {
		return workingExperience.stream().map(this::toDto).collect(Collectors.toList());
	}
}

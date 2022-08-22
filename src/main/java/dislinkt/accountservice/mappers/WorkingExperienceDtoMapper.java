package dislinkt.accountservice.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.entities.SeniorityLevel;
import dislinkt.accountservice.entities.WorkingExperience;

@Service
public class WorkingExperienceDtoMapper {

	public WorkingExperienceDto toDto(WorkingExperience workingExperience) {
		return new WorkingExperienceDto(workingExperience.getId(), workingExperience.getJobPosition(),
				workingExperience.getSeniority().ordinal(), workingExperience.getStartDate(),
				workingExperience.getEndDate());
	}

	public WorkingExperience toEntity(WorkingExperienceDto dto) {
		return new WorkingExperience(dto.getJobPosition(), SeniorityLevel.valueOfInt(dto.getSeniority()),
				dto.getStartDate(), dto.getEndDate());
	}
	
	public List<WorkingExperienceDto> toCollectionDto(Collection<WorkingExperience> workingExperience) {
		return workingExperience.stream().map(this::toDto).collect(Collectors.toList());
	}
}

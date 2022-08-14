package dislinkt.accountservice.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.entities.Education;
import dislinkt.accountservice.entities.FieldOfStudy;

@Service
public class EducationDtoMapper {

	public EducationDto toDto(Education education) {
		return new EducationDto(education.getId(), education.getSchool(), education.getField().ordinal(),
				education.getStartDate(), education.getEndDate(), education.getGrade());
	}

	public Education toEntity(EducationDto dto) {
		return new Education(dto.getSchool(), FieldOfStudy.valueOfInt(dto.getFieldOfStudy()), dto.getStartDate(),
				dto.getEndDate(), dto.getGrade());
	}
	
	public List<EducationDto> toCollectionDto(Collection<Education> educations) {
		return educations.stream().map(this::toDto).collect(Collectors.toList());
	}
}

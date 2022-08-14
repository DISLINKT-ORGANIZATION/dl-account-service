package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.dtos.ResumeDto;

public interface EducationService {

	ResumeDto updateEducation(EducationDto educationDto);
}

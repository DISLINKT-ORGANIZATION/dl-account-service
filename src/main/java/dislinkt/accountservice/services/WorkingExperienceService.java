package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.dtos.WorkingExperienceDto;

public interface WorkingExperienceService {

	ResumeDto updateWorkingExperience(WorkingExperienceDto workingExperienceDto);
}

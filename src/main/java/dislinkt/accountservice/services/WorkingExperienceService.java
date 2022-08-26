package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.exceptions.DateException;

import java.util.List;

public interface WorkingExperienceService {

	List<WorkingExperienceDto> updateWorkingExperience(Long userId, List<WorkingExperienceDto> experience);
	List<WorkingExperienceDto> getWorkingExperience(Long userId);
}

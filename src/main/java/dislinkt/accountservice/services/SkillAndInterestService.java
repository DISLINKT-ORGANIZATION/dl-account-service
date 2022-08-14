package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.dtos.SkillsAndInterestsDto;

public interface SkillAndInterestService {

	ResumeDto updateSkillsAndInterests(SkillsAndInterestsDto dto);
}

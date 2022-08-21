package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.dtos.SkillProficiencyDto;

import java.util.List;
import java.util.Map;

public interface SkillProficiencyService {

	AccountDto updateSkillProficiency(SkillProficiencyDto dto);
	List<SkillProficiencyDto> getAllSkillProficiencies();
	Map<String, List<SkillProficiencyDto>> getAllByType();
}

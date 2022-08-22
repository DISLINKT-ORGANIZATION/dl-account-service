package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.SkillProficiencyDto;

import java.util.List;
import java.util.Map;

public interface SkillProficiencyService {

    List<SkillProficiencyDto> getAllSkillProficiencies();

    Map<Integer, List<SkillProficiencyDto>> getAllByType();

    void updateProficiencies(Long userId, List<SkillProficiencyDto> skillProficiency);
}

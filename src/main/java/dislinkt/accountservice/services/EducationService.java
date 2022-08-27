package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.dtos.AccountDto;

import java.util.List;

public interface EducationService {

    List<EducationDto> getEducation(Long userId);
    List<EducationDto> updateEducation(Long userId, List<EducationDto> education);
}

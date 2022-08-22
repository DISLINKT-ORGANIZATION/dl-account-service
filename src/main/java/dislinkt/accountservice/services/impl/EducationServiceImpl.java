package dislinkt.accountservice.services.impl;

import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.entities.Education;
import dislinkt.accountservice.entities.FieldOfStudy;
import dislinkt.accountservice.entities.Account;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.exceptions.InconsistentData;
import dislinkt.accountservice.mappers.EducationDtoMapper;
import dislinkt.accountservice.mappers.AccountDtoMapper;
import dislinkt.accountservice.repositories.EducationRepository;
import dislinkt.accountservice.repositories.AccountRepository;
import dislinkt.accountservice.services.EducationService;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDtoMapper resumeMapper;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private EducationDtoMapper educationMapper;
	
	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	public AccountDto updateEducation(EducationDto educationDto) {
		Optional<Account> resumeOptional = accountRepository.findById(educationDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		OptionalLong educationIdOptional = account.getEducation().stream().mapToLong(education -> education.getId())
				.filter(id -> id == educationDto.getResumeId()).findFirst();
		Long dtoId = educationDto.getId() != null ? educationDto.getId() : 0;
		Optional<Education> educationOptional = educationRepository.findById(dtoId);
		if (educationOptional.isPresent() && !educationIdOptional.isPresent()) {
			throw new InconsistentData("Education exists but it is not matched to this resume.");
		}
		if (!educationOptional.isPresent()) {
			Education newEducation = educationMapper.toEntity(educationDto);
			newEducation.getAccounts().add(account);
			account.getEducation().add(newEducation);
			accountRepository.save(account);
		} else {
			Education education = educationOptional.get();
			education.setSchool(educationDto.getSchool());
			education.setField(FieldOfStudy.valueOfInt(educationDto.getFieldOfStudy()));
			education.setStartDate(educationDto.getStartDate());
			education.setEndDate(educationDto.getEndDate());
			education.setGrade(educationDto.getGrade());
			educationRepository.save(education);
		}
		return resumeMapper.toDto(accountRepository.findById(educationDto.getResumeId()).get());

	}
}

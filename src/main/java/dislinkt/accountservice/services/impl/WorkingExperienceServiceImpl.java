package dislinkt.accountservice.services.impl;

import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.entities.Account;
import dislinkt.accountservice.entities.SeniorityLevel;
import dislinkt.accountservice.entities.WorkingExperience;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.exceptions.InconsistentData;
import dislinkt.accountservice.mappers.AccountDtoMapper;
import dislinkt.accountservice.mappers.WorkingExperienceDtoMapper;
import dislinkt.accountservice.repositories.AccountRepository;
import dislinkt.accountservice.repositories.WorkingExperienceRepository;
import dislinkt.accountservice.services.WorkingExperienceService;

@Service
public class WorkingExperienceServiceImpl implements WorkingExperienceService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDtoMapper resumeMapper;

	@Autowired
	private WorkingExperienceRepository workingExperienceRepository;

	@Autowired
	private WorkingExperienceDtoMapper workingExperienceMapper;
	
	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	public AccountDto updateWorkingExperience(WorkingExperienceDto workingExperienceDto) {
		Optional<Account> resumeOptional = accountRepository.findById(workingExperienceDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		OptionalLong workingExperienceIdOptional = account.getWorkingExperiences().stream()
				.mapToLong(workingExperience -> workingExperience.getId())
				.filter(id -> id == workingExperienceDto.getResumeId()).findFirst();
		Long dtoId = workingExperienceDto.getId() != null ? workingExperienceDto.getId() : 0;
		Optional<WorkingExperience> workingExperienceOptional = workingExperienceRepository
				.findById(dtoId);
		if (workingExperienceOptional.isPresent() && !workingExperienceIdOptional.isPresent()) {
			throw new InconsistentData("Working experience exists but it is not matched to this resume.");
		}
		if (!workingExperienceOptional.isPresent()) {
			WorkingExperience newWorkingExperience = workingExperienceMapper.toEntity(workingExperienceDto);
			newWorkingExperience.getAccounts().add(account);
			account.getWorkingExperiences().add(newWorkingExperience);
			accountRepository.save(account);
		} else {
			WorkingExperience workingExperience = workingExperienceOptional.get();
			workingExperience.setJobPosition(workingExperienceDto.getJobPosition());
			workingExperience.setSeniority(SeniorityLevel.valueOfInt(workingExperienceDto.getSeniority()));
			workingExperience.setStartDate(workingExperienceDto.getStartDate());
			workingExperience.setEndDate(workingExperienceDto.getEndDate());
			workingExperienceRepository.save(workingExperience);
		}
		return resumeMapper.toDto(accountRepository.findById(workingExperienceDto.getResumeId()).get());

	}
}

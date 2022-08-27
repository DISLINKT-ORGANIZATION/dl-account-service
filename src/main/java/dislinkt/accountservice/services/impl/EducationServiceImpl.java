package dislinkt.accountservice.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import dislinkt.accountservice.entities.WorkingExperience;
import dislinkt.accountservice.exceptions.DateException;
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
	private AccountDtoMapper accountMapper;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private EducationDtoMapper educationMapper;

	@Override
	public List<EducationDto> getEducation(Long userId) {
		Account account = accountRepository.findByUserId(userId);
		if (Objects.isNull(account)) {
			throw new EntityNotFound("Account not found.");
		}
		List<Education> education = educationRepository.findAllByAccountId(account.getId());
		return educationMapper.toCollectionDto(education);
	}

	@Override
	public List<EducationDto> updateEducation(Long userId, List<EducationDto> education) {
		Account account = accountRepository.findByUserId(userId);
		if (Objects.isNull(account)) {
			throw new EntityNotFound("Account not found.");
		}
		List<Long> oldEducation = account.getEducation().stream().map(Education::getId)
				.collect(Collectors.toList());
		List<Education> newEducation = new ArrayList<>();
		Date today = new Date();
		for (EducationDto ed : education) {
			if (ed.getStartDate() > today.getTime()) {
				throw new DateException("Start end should not be in the future.");
			}
			if (ed.getEndDate() != -1 && ed.getEndDate() < ed.getStartDate()) {
				throw new DateException("Start end should be before end date.");
			}
			Education newEd = new Education();
			newEd.setAccount(account);
			newEd.setStartDate(ed.getStartDate());
			newEd.setEndDate(ed.getEndDate());
			newEd.setField(FieldOfStudy.valueOfInt(ed.getFieldOfStudy()));
			newEd.setSchool(ed.getSchool());
			newEd = this.educationRepository.save(newEd);
			newEducation.add(newEd);
		}
		account.setEducation(newEducation);
		this.accountRepository.save(account);
		for (Long id: oldEducation) {
			this.educationRepository.deleteById(id);
		}
		return educationMapper.toCollectionDto(newEducation);
	}
}

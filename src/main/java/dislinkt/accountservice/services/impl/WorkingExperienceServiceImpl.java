package dislinkt.accountservice.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import dislinkt.accountservice.dtos.SkillDto;
import dislinkt.accountservice.entities.*;
import dislinkt.accountservice.exceptions.DateException;
import dislinkt.accountservice.repositories.JobPositionRepository;
import dislinkt.accountservice.repositories.SkillRepository;
import org.bouncycastle.asn1.x500.style.AbstractX500NameStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.dtos.WorkingExperienceDto;
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
	private SkillRepository skillRepository;

	@Autowired
	private JobPositionRepository positionRepository;

	public List<WorkingExperienceDto> updateWorkingExperience(Long userId, List<WorkingExperienceDto> experience) {
		Account account = accountRepository.findByUserId(userId);
		if (Objects.isNull(account)) {
			throw new EntityNotFound("Account not found.");
		}

		List<Long> oldExperience = account.getWorkingExperience().stream().map(el -> el.getId())
				.collect(Collectors.toList());
		List<WorkingExperience> newExperience = new ArrayList<>();
		Date today = new Date();
		for (WorkingExperienceDto ex : experience) {
			if (ex.getStartDate() > today.getTime()) {
				throw new DateException("Start end should not be in the future.");
			}
			if (ex.getEndDate() != -1 && ex.getEndDate() < ex.getStartDate()) {
				throw new DateException("Start end should be before end date.");
			}
			List<Skill> skills = new ArrayList<>();
			for (SkillDto skillDto : ex.getSkills()) {
				Skill skill = skillRepository.findOneByName(skillDto.getName());
				skills.add(skill);
			}
			JobPosition position = this.positionRepository.findOneByTitle(ex.getPositionTitle());
			WorkingExperience exp = new WorkingExperience();
			exp.setAccount(account);
			exp.setStartDate(ex.getStartDate());
			exp.setEndDate(ex.getEndDate());
			exp.setSeniority(SeniorityLevel.valueOfInt(ex.getSeniority()));
			exp.setSkills(skills);
			exp.setPosition(position);
			exp = this.workingExperienceRepository.save(exp);
			newExperience.add(exp);
		}
		account.setWorkingExperience(newExperience);
		this.accountRepository.save(account);
		for (Long id : oldExperience) {
			this.workingExperienceRepository.deleteById(id);
		}
		return workingExperienceMapper.toCollectionDto(newExperience);
	}

	@Override
	public List<WorkingExperienceDto> getWorkingExperience(Long userId) {
		Account account = accountRepository.findByUserId(userId);
		if (Objects.isNull(account)) {
			throw new EntityNotFound("Account not found.");
		}
		List<WorkingExperience> experience = workingExperienceRepository.findAllByAccountId(account.getId());
		return workingExperienceMapper.toCollectionDto(experience);
	}
}

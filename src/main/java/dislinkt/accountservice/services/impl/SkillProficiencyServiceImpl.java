package dislinkt.accountservice.services.impl;

import java.util.*;
import java.util.stream.Collectors;

import dislinkt.accountservice.dtos.SkillProficiencyDto;
import dislinkt.accountservice.entities.*;
import dislinkt.accountservice.mappers.SkillProficiencyDtoMapper;
import dislinkt.accountservice.repositories.SkillProficiencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.dtos.SkillsAndInterestsDto;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.mappers.AccountDtoMapper;
import dislinkt.accountservice.repositories.InterestRepository;
import dislinkt.accountservice.repositories.AccountRepository;
import dislinkt.accountservice.repositories.SkillRepository;
import dislinkt.accountservice.services.SkillProficiencyService;

@Service
public class SkillProficiencyServiceImpl implements SkillProficiencyService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SkillProficiencyDtoMapper proficiencyMapper;

	@Autowired
	private SkillProficiencyRepository skillProficiencyRepository;


//	public AccountDto updateSkillProficiency(SkillProficiencyDto dto) {
//		Optional<Account> resumeOptional = accountRepository.findById(dto.getId());
//		if (!resumeOptional.isPresent()) {
//			throw new EntityNotFound("Resume not found.");
//		}
//		Account account = resumeOptional.get();
//		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
////		account.setSkills(new ArrayList<Skill>());
////
////
////		dto.getSkillsIds().forEach(skillId -> {
////			Optional<Skill> skillOptional = skillRepository.findById(skillId);
////			if (!skillOptional.isPresent()) {
////				throw new EntityNotFound("Skill not found.");
////			}
////			account.getSkills().add(skillOptional.get());
////		});
//
//		return resumeMapper.toDto(accountRepository.save(account));
//
//	}

	@Override
	public void updateProficiencies(Long userId, List<SkillProficiencyDto> skillProficiencies) {
		Account account = accountRepository.findByUserId(userId);
		if (account == null) {
			throw new EntityNotFound("Account not found.");
		}
		Set<SkillProficiency> newProficiencies = new HashSet<>();
		for (SkillProficiencyDto el: skillProficiencies) {
			String name = el.getName();
			Proficiency proficiency = Proficiency.valueOfInt(el.getSkillProficiency());
			SkillProficiency prof = this.skillProficiencyRepository.findOneBySkillNameAndProficiency(name, proficiency);
			newProficiencies.add(prof);
		}
		List<SkillProficiency> updatedProficiencies = new ArrayList<>(newProficiencies);
		account.setSkillProficiencies(updatedProficiencies);
		accountRepository.save(account);
	}

	@Override
	public List<SkillProficiencyDto> getAllSkillProficiencies() {
		List<SkillProficiency> proficiencies = skillProficiencyRepository.findAll();
		return proficiencyMapper.toCollectionDto(proficiencies);
	}

	public Map<Integer, List<SkillProficiencyDto>> getAllByType() {
		Map<Integer, List<SkillProficiencyDto>> map = new HashMap<>();
		List<SkillProficiency> elements = this.skillProficiencyRepository.findAll();
		for (SkillProficiency e : elements) {
			int type = e.getSkill().getType().getValue();
			SkillProficiencyDto dto = proficiencyMapper.toDto(e);
			if (map.containsKey(type)) {
				map.get(type).add(dto);
			} else {
				List<SkillProficiencyDto> newList = new ArrayList<>();
				newList.add(dto);
				map.put(type, newList);
			}
		}
		return map;
	}


}

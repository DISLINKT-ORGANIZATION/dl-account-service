package dislinkt.accountservice.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.dtos.SkillsAndInterestsDto;
import dislinkt.accountservice.entities.Interest;
import dislinkt.accountservice.entities.Resume;
import dislinkt.accountservice.entities.Skill;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.mappers.ResumeDtoMapper;
import dislinkt.accountservice.repositories.InterestRepository;
import dislinkt.accountservice.repositories.ResumeRepository;
import dislinkt.accountservice.repositories.SkillRepository;
import dislinkt.accountservice.services.SkillAndInterestService;

@Service
public class SkillAndInterestServiceImpl implements SkillAndInterestService {

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private ResumeDtoMapper resumeMapper;

	@Autowired
	private InterestRepository interestRepository;
	
	@Autowired
	private SkillRepository skillRepository;

	public ResumeDto updateSkillsAndInterests(SkillsAndInterestsDto dto) {
		Optional<Resume> resumeOptional = resumeRepository.findById(dto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		resume.setSkills(new ArrayList<Skill>());
		resume.setInterests(new ArrayList<Interest>());
		
		dto.getSkillsIds().forEach(skillId -> {
			Optional<Skill> skillOptional = skillRepository.findById(skillId);
			if (!skillOptional.isPresent()) {
				throw new EntityNotFound("Skill not found.");
			}
			resume.getSkills().add(skillOptional.get());
		});
		dto.getInterestsIds().forEach(interestId -> {
			Optional<Interest> interestOptional = interestRepository.findById(interestId);
			if (!interestOptional.isPresent()) {
				throw new EntityNotFound("Interest not found.");
			}
			resume.getInterests().add(interestOptional.get());
		});
		return resumeMapper.toDto(resumeRepository.save(resume));

	}
}

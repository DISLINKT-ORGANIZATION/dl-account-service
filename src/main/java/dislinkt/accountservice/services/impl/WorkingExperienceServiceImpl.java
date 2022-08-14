package dislinkt.accountservice.services.impl;

import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.entities.Resume;
import dislinkt.accountservice.entities.SeniorityLevel;
import dislinkt.accountservice.entities.WorkingExperience;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.exceptions.InconsistentData;
import dislinkt.accountservice.mappers.ResumeDtoMapper;
import dislinkt.accountservice.mappers.WorkingExperienceDtoMapper;
import dislinkt.accountservice.repositories.ResumeRepository;
import dislinkt.accountservice.repositories.WorkingExperienceRepository;
import dislinkt.accountservice.services.WorkingExperienceService;

@Service
public class WorkingExperienceServiceImpl implements WorkingExperienceService {

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private ResumeDtoMapper resumeMapper;

	@Autowired
	private WorkingExperienceRepository workingExperienceRepository;

	@Autowired
	private WorkingExperienceDtoMapper workingExperienceMapper;

	public ResumeDto updateWorkingExperience(WorkingExperienceDto workingExperienceDto) {
		Optional<Resume> resumeOptional = resumeRepository.findById(workingExperienceDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		OptionalLong workingExperienceIdOptional = resume.getWorkingExperiences().stream()
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
			newWorkingExperience.getResumes().add(resume);
			resume.getWorkingExperiences().add(newWorkingExperience);
			resumeRepository.save(resume);
		} else {
			WorkingExperience workingExperience = workingExperienceOptional.get();
			workingExperience.setJobPosition(workingExperienceDto.getJobPosition());
			workingExperience.setSeniority(SeniorityLevel.valueOfInt(workingExperienceDto.getSeniority()));
			workingExperience.setStartDate(workingExperienceDto.getStartDate());
			workingExperience.setEndDate(workingExperienceDto.getEndDate());
			workingExperienceRepository.save(workingExperience);
		}
		return resumeMapper.toDto(resumeRepository.findById(workingExperienceDto.getResumeId()).get());

	}
}

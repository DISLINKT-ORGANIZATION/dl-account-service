package dislinkt.accountservice.services.impl;

import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.entities.Education;
import dislinkt.accountservice.entities.FieldOfStudy;
import dislinkt.accountservice.entities.Resume;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.exceptions.InconsistentData;
import dislinkt.accountservice.mappers.EducationDtoMapper;
import dislinkt.accountservice.mappers.ResumeDtoMapper;
import dislinkt.accountservice.repositories.EducationRepository;
import dislinkt.accountservice.repositories.ResumeRepository;
import dislinkt.accountservice.services.EducationService;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private ResumeDtoMapper resumeMapper;

	@Autowired
	private EducationRepository educationRepository;

	@Autowired
	private EducationDtoMapper educationMapper;
	
	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	public ResumeDto updateEducation(EducationDto educationDto) {
		Optional<Resume> resumeOptional = resumeRepository.findById(educationDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		OptionalLong educationIdOptional = resume.getEducations().stream().mapToLong(education -> education.getId())
				.filter(id -> id == educationDto.getResumeId()).findFirst();
		Long dtoId = educationDto.getId() != null ? educationDto.getId() : 0;
		Optional<Education> educationOptional = educationRepository.findById(dtoId);
		if (educationOptional.isPresent() && !educationIdOptional.isPresent()) {
			throw new InconsistentData("Education exists but it is not matched to this resume.");
		}
		if (!educationOptional.isPresent()) {
			Education newEducation = educationMapper.toEntity(educationDto);
			newEducation.getResumes().add(resume);
			resume.getEducations().add(newEducation);
			resumeRepository.save(resume);
		} else {
			Education education = educationOptional.get();
			education.setSchool(educationDto.getSchool());
			education.setField(FieldOfStudy.valueOfInt(educationDto.getFieldOfStudy()));
			education.setStartDate(educationDto.getStartDate());
			education.setEndDate(educationDto.getEndDate());
			education.setGrade(educationDto.getGrade());
			educationRepository.save(education);
		}
		return resumeMapper.toDto(resumeRepository.findById(educationDto.getResumeId()).get());

	}
}

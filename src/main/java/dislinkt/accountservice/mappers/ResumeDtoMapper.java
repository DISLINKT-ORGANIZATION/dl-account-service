package dislinkt.accountservice.mappers;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.entities.Resume;

@Service
public class ResumeDtoMapper {

	@Autowired
	private InterestDtoMapper interestMapper;

	@Autowired
	private SkillDtoMapper skillMapper;

	@Autowired
	private EducationDtoMapper educationMapper;

	@Autowired
	private WorkingExperienceDtoMapper workingExperienceMapper;

	public ResumeDto toDto(Resume resume) {
		return new ResumeDto(resume.getId(), resume.getUserId(), resume.getBiography(), resume.getPhoneNumber(),
				resume.getPublicAccount(), resume.getMuteConnectionNotifications(),
				interestMapper.toCollectionDto(resume.getInterests()),
				skillMapper.toCollectionDto(resume.getSkills()),
				educationMapper.toCollectionDto(resume.getEducations()),
				workingExperienceMapper.toCollectionDto(resume.getWorkingExperiences()));
	}
}

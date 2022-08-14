package dislinkt.accountservice.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDto {

	private Long id;
	private Long userId;
	private String biography;
	private String phoneNumber;
	private Boolean publicAccount;
	private Boolean muteConnectionNotifications;
	private List<InterestDto> interests;
	private List<SkillDto> skills;
	private List<EducationDto> educations;
	private List<WorkingExperienceDto> workingExperiences;

}

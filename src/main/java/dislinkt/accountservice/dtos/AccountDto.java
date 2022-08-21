package dislinkt.accountservice.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

	private Long id;
	private Long userId;
	private String biography;
	private String phoneNumber;
	private Boolean publicAccount;
	private Boolean muteMessageNotifications;
	private Boolean mutePostNotifications;
	private List<InterestDto> interests;
	private List<SkillProficiencyDto> skills;
	private List<EducationDto> educations;
	private List<WorkingExperienceDto> workingExperiences;

}

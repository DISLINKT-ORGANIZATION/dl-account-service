package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingExperienceDto {

	private Long id;
	private String positionTitle;
	private Long positionId;
	private List<SkillDto> skills;
	private int seniority;
	private Long startDate;
	private Long endDate;
	
	public WorkingExperienceDto(
			Long id, Long userId,
			String positionTitle,
			Long positionId,
			List<SkillDto> skills,
			int seniority,
			Long startDate, Long endDate) {
		this.id = id;
		this.positionTitle = positionTitle;
		this.positionId = positionId;
		this.skills = skills;
		this.seniority = seniority;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}

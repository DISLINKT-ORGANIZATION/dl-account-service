package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingExperienceDto {

	private Long id;
	private Long resumeId;
	private String jobPosition;
	private int seniority;
	private Long startDate;
	private Long endDate;
	
	public WorkingExperienceDto(Long id, String jobPosition, int seniority, Long startDate, Long endDate) {
		super();
		this.id = id;
		this.jobPosition = jobPosition;
		this.seniority = seniority;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
}

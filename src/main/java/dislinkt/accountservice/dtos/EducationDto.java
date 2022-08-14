package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {

	private Long id;
	private Long resumeId;
	private String school;
	private int fieldOfStudy;
	private Long startDate;
	private Long endDate;
	private Float grade;
	
	public EducationDto(Long id, String school, int fieldOfStudy, Long startDate, Long endDate, Float grade) {
		super();
		this.id = id;
		this.school = school;
		this.fieldOfStudy = fieldOfStudy;
		this.startDate = startDate;
		this.endDate = endDate;
		this.grade = grade;
	}
	
}

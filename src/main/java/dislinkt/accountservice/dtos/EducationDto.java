package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto {

	private Long id;
	private String school;
	private int fieldOfStudy;
	private Long startDate;
	private Long endDate;

}

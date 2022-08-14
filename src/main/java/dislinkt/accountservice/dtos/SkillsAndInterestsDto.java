package dislinkt.accountservice.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillsAndInterestsDto {

	private Long resumeId;
	private List<Long> skillsIds;
	private List<Long> interestsIds;
}

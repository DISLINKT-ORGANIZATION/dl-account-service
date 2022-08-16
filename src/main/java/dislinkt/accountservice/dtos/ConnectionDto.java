package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {

	private Long resumeId;
	private Long connectionResumeId;
}

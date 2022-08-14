package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiographyDto {
	
	private Long resumeId;
	private String biography;
}

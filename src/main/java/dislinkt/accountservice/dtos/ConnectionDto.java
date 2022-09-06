package dislinkt.accountservice.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConnectionDto {

	private Long accountId;
	private Long accountConnectionId;
	private Long userConnectionId;

	public ConnectionDto(Long accountId, Long accountConnectionId) {
		this.accountId = accountId;
		this.accountConnectionId = accountConnectionId;
	}

	public ConnectionDto(Long accountId, Long accountConnectionId, Long userConnectionId) {
		this.accountId = accountId;
		this.accountConnectionId = accountConnectionId;
		this.userConnectionId = userConnectionId;
	}
}

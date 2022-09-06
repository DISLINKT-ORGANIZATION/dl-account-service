package dislinkt.accountservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequestDto {

	private Long connectionRequestId;
	private Long senderId;
	private Long receiverId;
	private Long senderAccountId;
	private Long receiverAccountId;
	private AccountDto accountDto;
}

package dislinkt.accountservice.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class JwtConfig {

	@Value("Authorization")
	private String header;

	@Value("Bearer ")
	private String prefix;

	@Value("86400")
	private int expiration;

	@Value("JwtSecretKey")
	private String secret;
}
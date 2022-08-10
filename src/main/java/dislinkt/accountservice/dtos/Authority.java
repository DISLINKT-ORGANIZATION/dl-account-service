package dislinkt.accountservice.dtos;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	Long id;

	String name;

	public String getAuthority() {
		return name;
	}

}

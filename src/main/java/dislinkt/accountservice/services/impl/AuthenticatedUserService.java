package dislinkt.accountservice.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.exceptions.InvalidToken;

@Service
public class AuthenticatedUserService {

	public void checkAuthenticatedUser(Long userId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long id = (Long) authentication.getPrincipal();
		if (id != userId) {
			throw new InvalidToken("Invalid user id");
		}
	}
}

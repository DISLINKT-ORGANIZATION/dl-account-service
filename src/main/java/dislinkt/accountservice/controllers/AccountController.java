package dislinkt.accountservice.controllers;

import dislinkt.accountservice.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import dislinkt.accountservice.services.AccountService;

import java.util.List;
import java.util.logging.Filter;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getResumeById(@PathVariable Long id) {
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getResumeByUserId(@PathVariable Long userId) {
		AccountDto accountDto = accountService.getAccountByUserId(userId);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/user/{userId}/biography")
	public ResponseEntity<?> updateBiography(@RequestBody BiographyDto updateDto, @PathVariable Long userId) {
		AccountDto accountDto = accountService.updateBiography(updateDto, userId);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/privacy/{accountId}")
	public ResponseEntity<?> changeAccountPrivacy(@PathVariable Long accountId) {
		AccountDto accountDto = accountService.changeAccountPrivacy(accountId);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/connection-notifications/{accountId}")
	public ResponseEntity<?> changeConnectionNotifications(@PathVariable Long accountId) {
		AccountDto accountDto = accountService.changeConnectionNotifications(accountId);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/search")
	public ResponseEntity<?> changeConnectionNotifications(@RequestBody FilterAccountsDto accountDto) {
		List<Long> filteredUserIds = accountService.filterUserIds(accountDto);
		return ResponseEntity.ok(filteredUserIds);
	}

}

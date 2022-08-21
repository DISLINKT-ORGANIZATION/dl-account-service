package dislinkt.accountservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import dislinkt.accountservice.dtos.ConnectionDto;
import dislinkt.accountservice.dtos.ConnectionRequestDto;
import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.services.ConnectionService;

@RestController
@RequestMapping("/connections")
public class ConnectionController {

	@Autowired
	private ConnectionService connectionService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/follow")
	public ResponseEntity<?> follow(@RequestBody ConnectionDto updateDto) {
		ConnectionDto connectionDto = connectionService.follow(updateDto);
		return ResponseEntity.ok(connectionDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/connection-requests/{resumeId}")
	public ResponseEntity<?> getUsersConnectionRequests(@PathVariable Long resumeId) {
		List<ConnectionRequestDto> connectionRequestsDtos = connectionService.getUsersConnectionRequests(resumeId);
		return ResponseEntity.ok(connectionRequestsDtos);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/accept-connection-request/{connectionRequestId}")
	public ResponseEntity<?> acceptConnectionRequest(@PathVariable Long connectionRequestId) {
		ConnectionDto connectionDto = connectionService.acceptConnectionRequest(connectionRequestId);
		return ResponseEntity.ok(connectionDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/decline-connection-request/{connectionRequestId}")
	public ResponseEntity<?> declineConnectionRequest(@PathVariable Long connectionRequestId) {
		ConnectionDto connectionDto = connectionService.declineConnectionRequest(connectionRequestId);
		return ResponseEntity.ok(connectionDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/unfollow")
	public ResponseEntity<?> unfollow(@RequestBody ConnectionDto updateDto) {
		connectionService.unfollow(updateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/mute-messages")
	public ResponseEntity<?> changeMuteMessages(@RequestBody ConnectionDto updateDto) {
		connectionService.changeMuteMessages(updateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/mute-posts")
	public ResponseEntity<?> changeMutePosts(@RequestBody ConnectionDto updateDto) {
		connectionService.changeMutePosts(updateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/block")
	public ResponseEntity<?> block(@RequestBody ConnectionDto updateDto) {
		connectionService.block(updateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/unblock")
	public ResponseEntity<?> unblock(@RequestBody ConnectionDto updateDto) {
		connectionService.unblock(updateDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/blocked-resumes/{resumeId}")
	public ResponseEntity<?> getBlockedResumes(@PathVariable Long resumeId) {
		List<AccountDto> accountDtos = connectionService.getBlockedResumes(resumeId);
		return ResponseEntity.ok(accountDtos);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-muted-posts")
	public ResponseEntity<?> checkIfMutedPosts(@RequestParam Long resumeId, @RequestParam Long connectionResumeId) {
		boolean isMutedPosts = connectionService.checkIfMutedPosts(resumeId, connectionResumeId);
		return ResponseEntity.ok(isMutedPosts);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-muted-messages")
	public ResponseEntity<?> checkIfMutedMessages(@RequestParam Long resumeId, @RequestParam Long connectionResumeId) {
		boolean isMutedMessages = connectionService.checkIfMutedMessages(resumeId, connectionResumeId);
		return ResponseEntity.ok(isMutedMessages);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-blocked")
	public ResponseEntity<?> checkIfBlocked(@RequestParam Long resumeId, @RequestParam Long connectionResumeId) {
		boolean isBlocked = connectionService.checkIfBlocked(resumeId, connectionResumeId);
		return ResponseEntity.ok(isBlocked);
	}

}

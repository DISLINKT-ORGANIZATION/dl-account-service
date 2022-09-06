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
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUsersConnections(@PathVariable Long userId) {
		List<ConnectionDto> connectionsDto = connectionService.getUsersConnections(userId);
		return ResponseEntity.ok(connectionsDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/follow/{loggedInUserId}/{requestedUserId}")
	public ResponseEntity<?> follow(@PathVariable Long loggedInUserId, @PathVariable Long requestedUserId) {
		boolean isFollowed = connectionService.follow(loggedInUserId, requestedUserId);
		return ResponseEntity.ok(isFollowed);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/connection-requests/{loggedInUserId}")
	public ResponseEntity<?> getUsersConnectionRequests(@PathVariable Long loggedInUserId) {
		List<ConnectionRequestDto> connectionRequestsDtos = connectionService.getUsersConnectionRequests(loggedInUserId);
		return ResponseEntity.ok(connectionRequestsDtos);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/accept-connection-request/{connectionRequestId}")
	public ResponseEntity<?> acceptConnectionRequest(@PathVariable Long connectionRequestId) {
		connectionService.acceptConnectionRequest(connectionRequestId);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/decline-connection-request/{connectionRequestId}")
	public ResponseEntity<?> declineConnectionRequest(@PathVariable Long connectionRequestId) {
		ConnectionDto connectionDto = connectionService.declineConnectionRequest(connectionRequestId);
		return ResponseEntity.ok(connectionDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/unfollow/{currentUserId}/{userId}")
	public ResponseEntity<?> unfollow(@PathVariable Long currentUserId, @PathVariable Long userId) {
		connectionService.unfollow(currentUserId, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/mute-messages/{userId1}/{userId2}")
	public ResponseEntity<?> changeMuteMessages(@PathVariable Long userId1, @PathVariable Long userId2) {
		boolean newValue = connectionService.changeMuteMessages(userId1, userId2);
		return ResponseEntity.ok(newValue);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/mute-posts/{userId1}/{userId2}")
	public ResponseEntity<?> changeMutePosts(@PathVariable Long userId1, @PathVariable Long userId2) {
		boolean newValue = connectionService.changeMutePosts(userId1, userId2);
		return ResponseEntity.ok(newValue);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/block/{loggedInUserId}/{blockedUserId}")
	public ResponseEntity<?> block(@PathVariable Long loggedInUserId, @PathVariable Long blockedUserId) {
		connectionService.block(loggedInUserId, blockedUserId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/unblock/{loggedInUserId}/{blockedUserId}")
	public ResponseEntity<?> unblock(@PathVariable Long loggedInUserId, @PathVariable Long blockedUserId) {
		connectionService.unblock(loggedInUserId, blockedUserId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{userId}/blocked-accounts")
	public ResponseEntity<?> getBlockedResumes(@PathVariable Long userId) {
		List<AccountDto> accountDtos = connectionService.getBlockedResumes(userId);
		return ResponseEntity.ok(accountDtos);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-muted-posts/{userId1}/{userId2}")
	public ResponseEntity<?> checkIfMutedPosts(@PathVariable Long userId1, @PathVariable Long userId2) {
		boolean isMutedPosts = connectionService.checkIfMutedPosts(userId1, userId2);
		return ResponseEntity.ok(isMutedPosts);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-muted-messages/{userId1}/{userId2}")
	public ResponseEntity<?> checkIfMutedMessages(@PathVariable Long userId1, @PathVariable Long userId2) {
		boolean isMutedMessages = connectionService.checkIfMutedMessages(userId1, userId2);
		return ResponseEntity.ok(isMutedMessages);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-blocked")
	public ResponseEntity<?> checkIfBlocked(@RequestParam Long resumeId, @RequestParam Long connectionResumeId) {
		boolean isBlocked = connectionService.checkIfBlocked(resumeId, connectionResumeId);
		return ResponseEntity.ok(isBlocked);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/check-connected/{userId1}/{userId2}")
	public ResponseEntity<?> checkIfConnected(@PathVariable Long userId1, @PathVariable Long userId2) {
		Long isConnected = connectionService.checkIfConnected(userId1, userId2);
		return ResponseEntity.ok(isConnected);
	}
}

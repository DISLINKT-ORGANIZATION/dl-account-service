package dislinkt.accountservice.services;

import java.util.List;

import dislinkt.accountservice.dtos.ConnectionDto;
import dislinkt.accountservice.dtos.ConnectionRequestDto;
import dislinkt.accountservice.dtos.AccountDto;

public interface ConnectionService {

	boolean follow(Long currentUserId, Long userId);
	
	List<ConnectionRequestDto> getUsersConnectionRequests(Long loggedInUserId);
	
	void acceptConnectionRequest(Long connectionRequestId);
	
	ConnectionDto declineConnectionRequest(Long connectionRequestId);
	
	void unfollow(Long currentUserId, Long userId);
	
	boolean changeMuteMessages(Long userId1, Long userId2);
	
	boolean changeMutePosts(Long userId1, Long userId2);
	
	void block(Long userId1, Long userId2);
	
	void unblock(Long userId1, Long userId2);
	
	List<AccountDto> getBlockedResumes(Long userId);
	
	boolean checkIfMutedPosts(Long userId1, Long userId2);
	
	boolean checkIfMutedMessages(Long userId1, Long userId2);
	
	boolean checkIfBlocked(Long resumeId, Long connectionResumeId);

	List<ConnectionDto> getUsersConnections(Long userId);

    Long checkIfConnected(Long userId1, Long userId2);

}

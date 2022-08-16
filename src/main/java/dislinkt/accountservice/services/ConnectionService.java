package dislinkt.accountservice.services;

import java.util.List;

import dislinkt.accountservice.dtos.ConnectionDto;
import dislinkt.accountservice.dtos.ConnectionRequestDto;
import dislinkt.accountservice.dtos.ResumeDto;

public interface ConnectionService {

	ConnectionDto follow(ConnectionDto connectionDto);
	
	List<ConnectionRequestDto> getUsersConnectionRequests(Long resumeId);
	
	ConnectionDto acceptConnectionRequest(Long connectionRequestId);
	
	ConnectionDto declineConnectionRequest(Long connectionRequestId);
	
	void unfollow(ConnectionDto connectionDto);
	
	void changeMuteMessages(ConnectionDto connectionDto);
	
	void changeMutePosts(ConnectionDto connectionDto);
	
	void block(ConnectionDto connectionDto);
	
	void unblock(ConnectionDto connectionDto);
	
	List<ResumeDto> getBlockedResumes(Long resumeId);
	
	boolean checkIfMutedPosts(Long resumeId, Long connectionResumeId);
	
	boolean checkIfMutedMessages(Long resumeId, Long connectionResumeId);
	
	boolean checkIfBlocked(Long resumeId, Long connectionResumeId);
}

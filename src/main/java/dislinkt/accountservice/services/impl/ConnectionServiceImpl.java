package dislinkt.accountservice.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.ConnectionDto;
import dislinkt.accountservice.dtos.ConnectionRequestDto;
import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.entities.Connection;
import dislinkt.accountservice.entities.ConnectionRequest;
import dislinkt.accountservice.entities.Resume;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.mappers.ResumeDtoMapper;
import dislinkt.accountservice.repositories.ConnectionRepository;
import dislinkt.accountservice.repositories.ConnectionRequestRepository;
import dislinkt.accountservice.repositories.ResumeRepository;
import dislinkt.accountservice.services.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService {

	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private ConnectionRequestRepository connectionRequestRepository;

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private ResumeDtoMapper resumeMapper;

	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	public ConnectionDto follow(ConnectionDto connectionDto) {
		if (checkIfBlocked(connectionDto.getConnectionResumeId(), connectionDto.getResumeId())) {
			throw new EntityNotFound("User is blocked");
		}
		Resume resume = getResume(connectionDto.getResumeId());
		Resume connectionResume = getResume(connectionDto.getConnectionResumeId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		if (connectionResume.getPublicAccount()) {
			return createConnection(connectionDto.getResumeId(), connectionDto.getConnectionResumeId());
		} else {
			return createConnectionRequest(connectionDto.getResumeId(), connectionDto.getConnectionResumeId());
		}
	}

	public List<ConnectionRequestDto> getUsersConnectionRequests(Long resumeId) {
		Resume resume = getResume(resumeId);
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		List<ConnectionRequest> requests = connectionRequestRepository.findByReceiverId(resumeId);
		List<ConnectionRequestDto> dtos = requests.stream().map(request -> {
			Resume senderResume = getResume(request.getSenderId());
			return new ConnectionRequestDto(request.getId(), resumeMapper.toDto(senderResume));
		}).collect(Collectors.toList());
		return dtos;
	}

	public ConnectionDto acceptConnectionRequest(Long connectionRequestId) {
		Optional<ConnectionRequest> connectionRequestOptional = connectionRequestRepository
				.findById(connectionRequestId);
		if (!connectionRequestOptional.isPresent()) {
			throw new EntityNotFound("Connection request does not exist.");
		}
		ConnectionRequest connectionRequest = connectionRequestOptional.get();
		Resume resume = getResume(connectionRequest.getReceiverId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		connectionRequest.setAccepted(true);
		connectionRequestRepository.save(connectionRequest);
		return createConnection(connectionRequest.getSenderId(), connectionRequest.getReceiverId());
	}

	public ConnectionDto declineConnectionRequest(Long connectionRequestId) {
		Optional<ConnectionRequest> connectionRequestOptional = connectionRequestRepository
				.findById(connectionRequestId);
		if (!connectionRequestOptional.isPresent()) {
			throw new EntityNotFound("Connection request does not exist.");
		}
		ConnectionRequest connectionRequest = connectionRequestOptional.get();
		Resume resume = getResume(connectionRequest.getReceiverId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		connectionRequestRepository.delete(connectionRequest);
		return new ConnectionDto(connectionRequest.getSenderId(), connectionRequest.getReceiverId());
	}

	public void unfollow(ConnectionDto connectionDto) {
		Resume resume = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		Optional<Connection> connectionOptional = resume.getConnections().stream()
				.filter(item -> item.getFollowedResumeId() == connectionDto.getConnectionResumeId()).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		Connection connection = connectionOptional.get();
		resume.getConnections().remove(connection);
		resumeRepository.save(resume);
		connectionRepository.delete(connection);
	}

	public void changeMuteMessages(ConnectionDto connectionDto) {
		Resume resume = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		Optional<Connection> connectionOptional = resume.getConnections().stream()
				.filter(item -> item.getFollowedResumeId() == connectionDto.getConnectionResumeId()).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		Connection connection = connectionOptional.get();
		connection.setMuteMessages(!connection.getMuteMessages());
		connectionRepository.save(connection);
	}

	public void changeMutePosts(ConnectionDto connectionDto) {
		Resume resume = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		Optional<Connection> connectionOptional = resume.getConnections().stream()
				.filter(item -> item.getId() == connectionDto.getConnectionResumeId()).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		Connection connection = connectionOptional.get();
		connection.setMutePosts(!connection.getMutePosts());
		connectionRepository.save(connection);
	}

	public void block(ConnectionDto connectionDto) {
		Resume resume = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		Resume connectionResume = getResume(connectionDto.getConnectionResumeId());
		resume.getBlockedAccounts().add(connectionResume);
		resumeRepository.save(resume);
	}

	public void unblock(ConnectionDto connectionDto) {
		Resume resume = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		Resume connectionResume = getResume(connectionDto.getConnectionResumeId());
		resume.getBlockedAccounts().remove(connectionResume);
		resumeRepository.save(resume);
	}

	public List<ResumeDto> getBlockedResumes(Long resumeId) {
		Resume resume = getResume(resumeId);
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		return resume.getBlockedAccounts().stream().map(item -> resumeMapper.toDto(item)).collect(Collectors.toList());
	}

	public boolean checkIfMutedPosts(Long resumeId, Long connectionResumeId) {
		Resume resume = getResume(resumeId);
		Optional<Connection> connection = resume.getConnections().stream()
				.filter(item -> item.getFollowedResumeId() == connectionResumeId).findFirst();
		if (!connection.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		return connection.get().getMutePosts();
	}

	public boolean checkIfMutedMessages(Long resumeId, Long connectionResumeId) {
		Resume resume = getResume(resumeId);
		Optional<Connection> connection = resume.getConnections().stream()
				.filter(item -> item.getFollowedResumeId() == connectionResumeId).findFirst();
		if (!connection.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		return connection.get().getMuteMessages();
	}

	public boolean checkIfBlocked(Long resumeId, Long connectionResumeId) {
		Resume resume = getResume(resumeId);
		Long blockedFound = resume.getBlockedAccounts().stream()
				.filter(blockedResume -> blockedResume.getId() == connectionResumeId).count();
		if (blockedFound != 0) {
			return true;
		}
		return false;
	}

	private ConnectionDto createConnectionRequest(Long resumeId, Long connectionResumeId) {
		ConnectionRequest newConnectionRequest = new ConnectionRequest(resumeId, connectionResumeId, false);
		List<ConnectionRequest> requestExists = connectionRequestRepository.findBySenderAndReceiver(resumeId,
				connectionResumeId);
		if (requestExists.size() == 0) {
			connectionRequestRepository.save(newConnectionRequest);
		}
		return new ConnectionDto(resumeId, connectionResumeId);
	}

	private ConnectionDto createConnection(Long resumeId, Long connectionResumeId) {
		Resume resume = getResume(resumeId);
		Long connectionExists = resume.getConnections().stream()
				.filter(connection -> connection.getFollowedResumeId() == connectionResumeId).count();
		if (connectionExists == 0) {
			Connection newConnection = new Connection(connectionResumeId, false, false);
			resume.getConnections().add(newConnection);
			resumeRepository.save(resume);
		}
		return new ConnectionDto(resumeId, connectionResumeId);
	}

	private Resume getResume(Long resumeId) {
		Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume does not exist.");
		}
		Resume resume = resumeOptional.get();
		return resume;
	}
}

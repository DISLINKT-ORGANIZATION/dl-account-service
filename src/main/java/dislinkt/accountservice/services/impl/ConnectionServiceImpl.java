package dislinkt.accountservice.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.ConnectionDto;
import dislinkt.accountservice.dtos.ConnectionRequestDto;
import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.entities.Connection;
import dislinkt.accountservice.entities.ConnectionRequest;
import dislinkt.accountservice.entities.Account;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.mappers.AccountDtoMapper;
import dislinkt.accountservice.repositories.ConnectionRepository;
import dislinkt.accountservice.repositories.ConnectionRequestRepository;
import dislinkt.accountservice.repositories.AccountRepository;
import dislinkt.accountservice.services.ConnectionService;

@Service
public class ConnectionServiceImpl implements ConnectionService {

	@Autowired
	private ConnectionRepository connectionRepository;

	@Autowired
	private ConnectionRequestRepository connectionRequestRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountDtoMapper resumeMapper;

	@Autowired
	private AuthenticatedUserService authenticatedUserService;

	public ConnectionDto follow(ConnectionDto connectionDto) {
		if (checkIfBlocked(connectionDto.getConnectionResumeId(), connectionDto.getResumeId())) {
			throw new EntityNotFound("User is blocked");
		}
		Account account = getResume(connectionDto.getResumeId());
		Account connectionAccount = getResume(connectionDto.getConnectionResumeId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		if (connectionAccount.getPublicAccount()) {
			return createConnection(connectionDto.getResumeId(), connectionDto.getConnectionResumeId());
		} else {
			return createConnectionRequest(connectionDto.getResumeId(), connectionDto.getConnectionResumeId());
		}
	}

	public List<ConnectionRequestDto> getUsersConnectionRequests(Long resumeId) {
		Account account = getResume(resumeId);
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		List<ConnectionRequest> requests = connectionRequestRepository.findByReceiverId(resumeId);
		List<ConnectionRequestDto> dtos = requests.stream().map(request -> {
			Account senderAccount = getResume(request.getSenderId());
			return new ConnectionRequestDto(request.getId(), resumeMapper.toDto(senderAccount));
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
		Account account = getResume(connectionRequest.getReceiverId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
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
		Account account = getResume(connectionRequest.getReceiverId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		connectionRequestRepository.delete(connectionRequest);
		return new ConnectionDto(connectionRequest.getSenderId(), connectionRequest.getReceiverId());
	}

	public void unfollow(ConnectionDto connectionDto) {
		Account account = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		Optional<Connection> connectionOptional = account.getConnections().stream()
				.filter(item -> item.getFollowedAccountId() == connectionDto.getConnectionResumeId()).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		Connection connection = connectionOptional.get();
		account.getConnections().remove(connection);
		accountRepository.save(account);
		connectionRepository.delete(connection);
	}

	public void changeMuteMessages(ConnectionDto connectionDto) {
		Account account = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		Optional<Connection> connectionOptional = account.getConnections().stream()
				.filter(item -> item.getFollowedAccountId() == connectionDto.getConnectionResumeId()).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		Connection connection = connectionOptional.get();
		connection.setMuteMessages(!connection.getMuteMessages());
		connectionRepository.save(connection);
	}

	public void changeMutePosts(ConnectionDto connectionDto) {
		Account account = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		Optional<Connection> connectionOptional = account.getConnections().stream()
				.filter(item -> item.getId() == connectionDto.getConnectionResumeId()).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		Connection connection = connectionOptional.get();
		connection.setMutePosts(!connection.getMutePosts());
		connectionRepository.save(connection);
	}

	public void block(ConnectionDto connectionDto) {
		Account account = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		Account connectionAccount = getResume(connectionDto.getConnectionResumeId());
		account.getBlockedAccounts().add(connectionAccount);
		accountRepository.save(account);
	}

	public void unblock(ConnectionDto connectionDto) {
		Account account = getResume(connectionDto.getResumeId());
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		Account connectionAccount = getResume(connectionDto.getConnectionResumeId());
		account.getBlockedAccounts().remove(connectionAccount);
		accountRepository.save(account);
	}

	public List<AccountDto> getBlockedResumes(Long resumeId) {
		Account account = getResume(resumeId);
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		return account.getBlockedAccounts().stream().map(item -> resumeMapper.toDto(item)).collect(Collectors.toList());
	}

	public boolean checkIfMutedPosts(Long resumeId, Long connectionResumeId) {
		Account account = getResume(resumeId);
		Optional<Connection> connection = account.getConnections().stream()
				.filter(item -> item.getFollowedAccountId() == connectionResumeId).findFirst();
		if (!connection.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		return connection.get().getMutePosts();
	}

	public boolean checkIfMutedMessages(Long resumeId, Long connectionResumeId) {
		Account account = getResume(resumeId);
		Optional<Connection> connection = account.getConnections().stream()
				.filter(item -> item.getFollowedAccountId() == connectionResumeId).findFirst();
		if (!connection.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		return connection.get().getMuteMessages();
	}

	public boolean checkIfBlocked(Long resumeId, Long connectionResumeId) {
		Account account = getResume(resumeId);
		Long blockedFound = account.getBlockedAccounts().stream()
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
		Account account = getResume(resumeId);
		Long connectionExists = account.getConnections().stream()
				.filter(connection -> connection.getFollowedAccountId() == connectionResumeId).count();
		if (connectionExists == 0) {
			Connection newConnection = new Connection(connectionResumeId, false, false);
			account.getConnections().add(newConnection);
			accountRepository.save(account);
		}
		return new ConnectionDto(resumeId, connectionResumeId);
	}

	private Account getResume(Long resumeId) {
		Optional<Account> resumeOptional = accountRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume does not exist.");
		}
		Account account = resumeOptional.get();
		return account;
	}
}

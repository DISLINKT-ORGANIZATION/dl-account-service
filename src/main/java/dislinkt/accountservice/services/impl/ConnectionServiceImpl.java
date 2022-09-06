package dislinkt.accountservice.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

	public List<ConnectionRequestDto> getUsersConnectionRequests(Long loggedInUserId) {
		Account acc = getAccountByUserId(loggedInUserId);
		List<ConnectionRequest> requests = connectionRequestRepository.findByReceiverId(acc.getId());
		return requests.stream().map(request -> {
			Account senderAccount = getResume(request.getSenderId());
			Account receiverAccount = getResume(request.getReceiverId());
			ConnectionRequestDto dto = new ConnectionRequestDto();
			dto.setConnectionRequestId(request.getId());
			dto.setReceiverId(receiverAccount.getUserId());
			dto.setReceiverAccountId(receiverAccount.getId());
			dto.setSenderId(senderAccount.getUserId());
			dto.setSenderAccountId(senderAccount.getId());
			return dto;
		}).collect(Collectors.toList());
	}

	public void acceptConnectionRequest(Long  connectionRequestId) {
		Optional<ConnectionRequest> connectionRequestOptional = connectionRequestRepository
				.findById(connectionRequestId);
		if (!connectionRequestOptional.isPresent()) {
			throw new EntityNotFound("Connection request does not exist.");
		}
		ConnectionRequest connectionRequest = connectionRequestOptional.get();
		connectionRequest.setAccepted(true);
		connectionRequestRepository.save(connectionRequest);
		Account account1 = getResume(connectionRequest.getSenderId());
		Account account2 = getResume(connectionRequest.getReceiverId());
		createConnection(account1, account2);
	}

	public ConnectionDto declineConnectionRequest(Long connectionRequestId) {
		Optional<ConnectionRequest> connectionRequestOptional = connectionRequestRepository
				.findById(connectionRequestId);
		if (!connectionRequestOptional.isPresent()) {
			throw new EntityNotFound("Connection request does not exist.");
		}
		ConnectionRequest connectionRequest = connectionRequestOptional.get();
		connectionRequestRepository.delete(connectionRequest);
		return new ConnectionDto(connectionRequest.getSenderId(), connectionRequest.getReceiverId());
	}

	public boolean follow(Long currentUserId, Long userId) {
		Account loggedInAccount = getAccountByUserId(currentUserId);
		Account accountToFollow = getAccountByUserId(userId);

		if (accountToFollow.getPublicAccount()) {
			createConnection(loggedInAccount, accountToFollow);
			return true;
		} else {
			createConnectionRequest(loggedInAccount, accountToFollow);
			return false;
		}
	}

	public void unfollow(Long currentUserId, Long userId) {
		Account loggedInAccount = getAccountByUserId(currentUserId);
		Account accountToUnfollow = getAccountByUserId(userId);

		Connection connection = getConnection(loggedInAccount, accountToUnfollow);
		Connection connectionReverse = getConnection(accountToUnfollow, loggedInAccount);

		loggedInAccount.getConnections().remove(connection);
		accountToUnfollow.getConnections().remove(connectionReverse);

		accountRepository.save(loggedInAccount);
		accountRepository.save(accountToUnfollow);
	}

	private Account getAccountByUserId(Long currentUserId) {
		Account loggedInAccount = this.accountRepository.findByUserId(currentUserId);
		if (Objects.isNull(loggedInAccount)) {
			throw new EntityNotFound("Account does not exist");
		}
		return loggedInAccount;
	}

	private Connection getConnection(Account loggedInAccount, Account accountToUnfollow) {
		Optional<Connection> connectionOptional = loggedInAccount.getConnections().stream()
				.filter(item -> Objects.equals(item.getFollowedAccountId(), accountToUnfollow.getId())).findFirst();
		if (!connectionOptional.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		return connectionOptional.get();
	}

	public boolean changeMuteMessages(Long userId1, Long userId2) {
		Connection connection = checkConnection(userId1, userId2);
		boolean newValue = !connection.getMuteMessages();
		connection.setMuteMessages(newValue);
		connectionRepository.save(connection);
		return newValue;
	}

	public boolean changeMutePosts(Long userId1, Long userId2) {
		Connection connection = checkConnection(userId1, userId2);
		boolean newValue = !connection.getMutePosts();
		connection.setMutePosts(newValue);
		connectionRepository.save(connection);
		return newValue;
	}

	public void block(Long loggedUserId, Long blockedUserId) {
		Account loggedInUserAccount = getAccountByUserId(loggedUserId);
		Account blockedInUserAccount = getAccountByUserId(blockedUserId);
		loggedInUserAccount.getBlockedAccounts().add(blockedInUserAccount);
		try {
			Connection connection = checkConnection(loggedUserId, blockedUserId);
			loggedInUserAccount.getConnections().remove(connection);
			connection = checkConnection(blockedUserId, loggedUserId);
			blockedInUserAccount.getConnections().remove(connection);
			accountRepository.save(blockedInUserAccount);
		} catch (Exception e) {
			System.out.println("Nice.");
		}
		accountRepository.save(loggedInUserAccount);
	}

	public void unblock(Long loggedUserId, Long blockedUserId) {
		Account loggedInUserAccount = getAccountByUserId(loggedUserId);
		Account blockedInUserAccount = getAccountByUserId(blockedUserId);
		loggedInUserAccount.getBlockedAccounts().remove(blockedInUserAccount);
		accountRepository.save(loggedInUserAccount);
	}

	public List<AccountDto> getBlockedResumes(Long userId) {
		Account account = getAccountByUserId(userId);
		return account.getBlockedAccounts().stream().map(item -> resumeMapper.toDto(item)).collect(Collectors.toList());
	}

	public boolean checkIfMutedPosts(Long userId1, Long userId2) {
		Connection connection = checkConnection(userId1, userId2);
		return connection.getMutePosts();
	}

	public boolean checkIfMutedMessages(Long userId1, Long userId2) {
		Connection connection = checkConnection(userId1, userId2);
		return connection.getMuteMessages();
	}

	private Connection checkConnection(Long userId1, Long userId2) {
		Account account1 = getAccountByUserId(userId1);
		Account account2 = getAccountByUserId(userId2);
		Optional<Connection> connection = account1.getConnections().stream()
				.filter(item -> item.getFollowedAccountId().equals(account2.getId())).findFirst();
		if (!connection.isPresent()) {
			throw new EntityNotFound("Connection does not exist.");
		}
		return connection.get();
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

	@Override
	public List<ConnectionDto> getUsersConnections(Long userId) {
		Account account = getAccountByUserId(userId);
		List<Connection> connections = account.getConnections();
		List<ConnectionDto> connectionsDto = new ArrayList<>();
		for (Connection conn : connections) {
			ConnectionDto dto = new ConnectionDto(account.getId(), conn.getFollowedAccountId());
			Optional<Account> followedAcc = this.accountRepository.findById(conn.getFollowedAccountId());
			if (!followedAcc.isPresent()) {
				throw new EntityNotFound("Followed account does not exist.");
			}
			dto.setUserConnectionId(followedAcc.get().getUserId());
			connectionsDto.add(dto);
		}
		return connectionsDto;
	}

	@Override
	public Long checkIfConnected(Long userId1, Long userId2) {
		// 0 - not followed, 1 - followed, 2 - pending request
		// lazy developer, no enums
		Account acc1 = getAccountByUserId(userId1);
		Account acc2 = getAccountByUserId(userId2);
		List<Connection> connections = acc1.getConnections();
		for (Connection conn: connections) {
			if (conn.getFollowedAccountId().equals(acc2.getId())) {
				return 1L;
			}
		}
		List<ConnectionRequest> requestExists = connectionRequestRepository.findBySenderAndReceiver(acc1.getId(),
				acc2.getId());
		if (requestExists.size() != 0) {
			return 2L;
		}
		return 0L;
	}


	private void createConnectionRequest(Account loggedInAccount, Account accountToFollow) {
		ConnectionRequest newConnectionRequest = new ConnectionRequest(loggedInAccount.getId(), accountToFollow.getId(), false);
		List<ConnectionRequest> requestExists = connectionRequestRepository.findBySenderAndReceiver(loggedInAccount.getId(),
				accountToFollow.getId());
		if (requestExists.size() == 0) {
			connectionRequestRepository.save(newConnectionRequest);
		}
	}

	private void createConnection(Account loggedInAccount, Account accountToFollow) {
		long connectionExists = loggedInAccount.getConnections().stream()
				.filter(connection -> Objects.equals(connection.getFollowedAccountId(), accountToFollow.getId())).count();
		if (connectionExists == 0) {
			Connection newConnection = new Connection(accountToFollow.getId(), false, false);
			loggedInAccount.getConnections().add(newConnection);
			accountRepository.save(loggedInAccount);
			Connection reverseConnection = new Connection(loggedInAccount.getId(), false, false);
			accountToFollow.getConnections().add(reverseConnection);
			accountRepository.save(accountToFollow);
		}
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

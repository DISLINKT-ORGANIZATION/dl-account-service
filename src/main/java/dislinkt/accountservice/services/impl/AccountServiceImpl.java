package dislinkt.accountservice.services.impl;

import dislinkt.accountservice.dtos.FilterAccountsDto;
import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.BiographyDto;
import dislinkt.accountservice.dtos.PhoneNumberDto;
import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.entities.Account;
import dislinkt.accountservice.exceptions.AccountAlreadyExists;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.mappers.AccountDtoMapper;
import dislinkt.accountservice.repositories.AccountRepository;
import dislinkt.accountservice.services.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountDtoMapper resumeMapper;
	
	public AccountDto createAccount(Long userId) {
		Account account = accountRepository.findByUserId(userId);
		if (account != null) {
			throw new AccountAlreadyExists("Resume already exists.");
		}
		Account newAccount = new Account(userId);
		return resumeMapper.toDto(accountRepository.save(newAccount));
	}
	
	public AccountDto getAccountById(Long id) {
		Optional<Account> resumeOptional = accountRepository.findById(id);
		if (resumeOptional.isPresent()) {
			Account account = resumeOptional.get();
			return resumeMapper.toDto(account);
		}
		return null;
	}
	
	public AccountDto getAccountByUserId(Long userId) {
		Account account = accountRepository.findByUserId(userId);
		if (account != null) {
			return resumeMapper.toDto(account);
		}
		return null;
	}
	
	public AccountDto updateBiography(BiographyDto biographyDto, Long userId) {
		Account account = accountRepository.findByUserId(userId);
		if (Objects.isNull(account)) {
			throw new EntityNotFound("Account not found.");
		}
		account.setBiography(biographyDto.getBiography());
		accountRepository.save(account);
		return resumeMapper.toDto(account);
	}
	
	public AccountDto changeAccountPrivacy(Long resumeId) {
		Optional<Account> resumeOptional = accountRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		account.setPublicAccount(!account.getPublicAccount());
		accountRepository.save(account);
		return resumeMapper.toDto(account);
	}
	
	public AccountDto changeConnectionNotifications(Long resumeId) {
		Optional<Account> resumeOptional = accountRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		account.setMuteMessageNotifications(!account.getMuteMessageNotifications());
		account.setMutePostNotifications(!account.getMutePostNotifications());
		accountRepository.save(account);
		return resumeMapper.toDto(account);
	}

	@Override
	public List<Long> filterUserIds(FilterAccountsDto accountDto) {
		Account loggedInAccount = findAccountByUserId(accountDto.getLoggedInUserId());
		List<Long> filteredIds = new ArrayList<>();
		for (Long id: accountDto.getUsersIds()) {
			Account currentAcc = findAccountByUserId(id);
			if (loggedInAccount.getBlockedAccounts().contains(currentAcc)) {
				continue;
			}
			if (isInBlockList(loggedInAccount, currentAcc)) {
				continue;
			}
			filteredIds.add(id);
		}
		return filteredIds;
	}

	private boolean isInBlockList(Account loggedInAccount, Account currentAccount) {
		return currentAccount.getBlockedAccounts().contains(loggedInAccount);
	}

	private Account findAccountByUserId(Long userId) {
		Account account = this.accountRepository.findByUserId(userId);
		if (Objects.isNull(account)) {
			throw new EntityNotFound("Account not found.");
		}
		return account;
	}

}

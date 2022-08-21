package dislinkt.accountservice.services.impl;

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

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountDtoMapper resumeMapper;
	
	@Autowired
	private AuthenticatedUserService authenticatedUserService;
	
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
			authenticatedUserService.checkAuthenticatedUser(account.getUserId());
			return resumeMapper.toDto(account);
		}
		return null;
	}
	
	public AccountDto getAccountByUserId(Long userId) {
		Account account = accountRepository.findByUserId(userId);
		if (account != null) {
			authenticatedUserService.checkAuthenticatedUser(account.getUserId());
			return resumeMapper.toDto(account);
		}
		return null;
	}
	
	public AccountDto updateBiography(BiographyDto biographyDto) {
		Optional<Account> resumeOptional = accountRepository.findById(biographyDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		account.setBiography(biographyDto.getBiography());
		accountRepository.save(account);
		return resumeMapper.toDto(account);
	}
	
	public AccountDto updatePhoneNumber(PhoneNumberDto phoneNumberDto) {
		Optional<Account> resumeOptional = accountRepository.findById(phoneNumberDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		account.setPhoneNumber(phoneNumberDto.getPhoneNumber());
		accountRepository.save(account);
		return resumeMapper.toDto(account);
	}
	
	public AccountDto changeAccountPrivacy(Long resumeId) {
		Optional<Account> resumeOptional = accountRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Account account = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
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
		authenticatedUserService.checkAuthenticatedUser(account.getUserId());
		account.setMuteMessageNotifications(!account.getMuteMessageNotifications());
		account.setMutePostNotifications(!account.getMutePostNotifications());
		accountRepository.save(account);
		return resumeMapper.toDto(account);
	}
}

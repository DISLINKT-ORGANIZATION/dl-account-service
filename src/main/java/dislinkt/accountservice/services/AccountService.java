package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.BiographyDto;
import dislinkt.accountservice.dtos.PhoneNumberDto;
import dislinkt.accountservice.dtos.AccountDto;

public interface AccountService {

	AccountDto createAccount(Long userId);

	AccountDto getAccountById(Long id);

	AccountDto getAccountByUserId(Long userId);
	
	AccountDto updateBiography(BiographyDto biographyDto, Long userId);

	AccountDto changeAccountPrivacy(Long accountId);
	
	AccountDto changeConnectionNotifications(Long accountId);
}

package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.BiographyDto;
import dislinkt.accountservice.dtos.FilterAccountsDto;
import dislinkt.accountservice.dtos.PhoneNumberDto;
import dislinkt.accountservice.dtos.AccountDto;

import java.util.List;

public interface AccountService {

	AccountDto createAccount(Long userId);

	AccountDto getAccountById(Long id);

	AccountDto getAccountByUserId(Long userId);
	
	AccountDto updateBiography(BiographyDto biographyDto, Long userId);

	AccountDto changeAccountPrivacy(Long userId);
	
	AccountDto changeConnectionNotifications(Long accountId);

	List<Long> filterUserIds(FilterAccountsDto accountDto);

    List<Long> filterPublicUserIds(FilterAccountsDto accountDto);
}

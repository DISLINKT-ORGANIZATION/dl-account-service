package dislinkt.accountservice.mappers;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.entities.Account;

@Service
public class AccountDtoMapper {

	@Autowired
	private InterestDtoMapper interestMapper;

	@Autowired
	private SkillProficiencyDtoMapper skillMapper;

	@Autowired
	private EducationDtoMapper educationMapper;

	@Autowired
	private WorkingExperienceDtoMapper workingExperienceMapper;

	public AccountDto toDto(Account account) {
		return new AccountDto(account.getId(), account.getUserId(), account.getBiography(), account.getPhoneNumber(),
				account.getPublicAccount(), account.getMuteMessageNotifications(), account.getMutePostNotifications(),
				interestMapper.toCollectionDto(account.getInterests()),
				skillMapper.toCollectionDto(account.getSkillProficiencies()),
				educationMapper.toCollectionDto(account.getEducation()),
				workingExperienceMapper.toCollectionDto(account.getWorkingExperience()));
	}
}

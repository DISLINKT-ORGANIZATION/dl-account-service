package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.BiographyDto;
import dislinkt.accountservice.dtos.PhoneNumberDto;
import dislinkt.accountservice.dtos.ResumeDto;

public interface ResumeService {

	ResumeDto createResume(Long userId);

	ResumeDto getResumeById(Long id);

	ResumeDto getResumeByUserId(Long userId);
	
	ResumeDto updateBiography(BiographyDto biographyDto);
	
	ResumeDto updatePhoneNumber(PhoneNumberDto phoneNumberDto);
	
	ResumeDto changeAccountPrivacy(Long resumeId);
	
	ResumeDto changeConnectionNotifications(Long resumeId);
}

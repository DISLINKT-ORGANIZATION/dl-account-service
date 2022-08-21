package dislinkt.accountservice.controllers;

import dislinkt.accountservice.dtos.*;
import dislinkt.accountservice.entities.SkillProficiency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dislinkt.accountservice.services.EducationService;
import dislinkt.accountservice.services.AccountService;
import dislinkt.accountservice.services.SkillProficiencyService;
import dislinkt.accountservice.services.WorkingExperienceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService resumeService;
	
	@Autowired
	private WorkingExperienceService workingExperienceService;
	
	@Autowired
	private EducationService educationService;

	@Autowired
	private SkillProficiencyService skillProficiencyService;
	
	@Autowired
	private SkillProficiencyService skillAndInterestService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getResumeById(@PathVariable Long id) {
		AccountDto accountDto = resumeService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getResumeByUserId(@PathVariable Long userId) {
		AccountDto accountDto = resumeService.getAccountByUserId(userId);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/biography")
	public ResponseEntity<?> updateBiography(@RequestBody BiographyDto updateDto) {
		AccountDto accountDto = resumeService.updateBiography(updateDto);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/phone-number")
	public ResponseEntity<?> updatePhoneNumber(@RequestBody PhoneNumberDto updateDto) {
		AccountDto accountDto = resumeService.updatePhoneNumber(updateDto);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/privacy/{accountId}")
	public ResponseEntity<?> changeAccountPrivacy(@PathVariable Long accountId) {
		AccountDto accountDto = resumeService.changeAccountPrivacy(accountId);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/connection-notifications/{accountId}")
	public ResponseEntity<?> changeConnectionNotifications(@PathVariable Long accountId) {
		AccountDto accountDto = resumeService.changeConnectionNotifications(accountId);
		return ResponseEntity.ok(accountDto);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/working-experience")
	public ResponseEntity<?> updateWorkingExperience(@RequestBody WorkingExperienceDto updateDto) {
		AccountDto accountDto = workingExperienceService.updateWorkingExperience(updateDto);
		return ResponseEntity.ok(accountDto);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/education")
	public ResponseEntity<?> updateEducation(@RequestBody EducationDto updateDto) {
		AccountDto accountDto = educationService.updateEducation(updateDto);
		return ResponseEntity.ok(accountDto);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/skills-and-interests")
	public ResponseEntity<?> updateSkillsAndInterests(@RequestBody SkillProficiencyDto updateDto) {
		AccountDto accountDto = skillAndInterestService.updateSkillProficiency(updateDto);
		return ResponseEntity.ok(accountDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/skill-proficiencies")
	public ResponseEntity<Object> retrieveAllSkillProficiencies() {
		List<SkillProficiencyDto> dto = skillProficiencyService.getAllSkillProficiencies();
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/skill-proficiencies/type")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Map<String, List<SkillProficiencyDto>>> getAllByType() {
		Map<String, List<SkillProficiencyDto>> dtos = skillProficiencyService.getAllByType();
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}

}

package dislinkt.accountservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dislinkt.accountservice.dtos.BiographyDto;
import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.dtos.PhoneNumberDto;
import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.dtos.SkillsAndInterestsDto;
import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.services.EducationService;
import dislinkt.accountservice.services.ResumeService;
import dislinkt.accountservice.services.SkillAndInterestService;
import dislinkt.accountservice.services.WorkingExperienceService;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private WorkingExperienceService workingExperienceService;
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private SkillAndInterestService skillAndInterestService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getResumeById(@PathVariable Long id) {
		ResumeDto resumeDto = resumeService.getResumeById(id);
		return ResponseEntity.ok(resumeDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user-id/{userId}")
	public ResponseEntity<?> getResumeByUserId(@PathVariable Long userId) {
		ResumeDto resumeDto = resumeService.getResumeByUserId(userId);
		return ResponseEntity.ok(resumeDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update-biography")
	public ResponseEntity<?> updateBiography(@RequestBody BiographyDto updateDto) {
		ResumeDto resumeDto = resumeService.updateBiography(updateDto);
		return ResponseEntity.ok(resumeDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update-phone-number")
	public ResponseEntity<?> updatePhoneNumber(@RequestBody PhoneNumberDto updateDto) {
		ResumeDto resumeDto = resumeService.updatePhoneNumber(updateDto);
		return ResponseEntity.ok(resumeDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/account-privacy/{resumeId}")
	public ResponseEntity<?> changeAccountPrivacy(@PathVariable Long resumeId) {
		ResumeDto resumeDto = resumeService.changeAccountPrivacy(resumeId);
		return ResponseEntity.ok(resumeDto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/connection-notifications/{resumeId}")
	public ResponseEntity<?> changeConnectionNotifications(@PathVariable Long resumeId) {
		ResumeDto resumeDto = resumeService.changeConnectionNotifications(resumeId);
		return ResponseEntity.ok(resumeDto);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update-working-experience")
	public ResponseEntity<?> updateWorkingExperience(@RequestBody WorkingExperienceDto updateDto) {
		ResumeDto resumeDto = workingExperienceService.updateWorkingExperience(updateDto);
		return ResponseEntity.ok(resumeDto);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update-education")
	public ResponseEntity<?> updateEducation(@RequestBody EducationDto updateDto) {
		ResumeDto resumeDto = educationService.updateEducation(updateDto);
		return ResponseEntity.ok(resumeDto);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/update-skills-and-interests")
	public ResponseEntity<?> updateSkillsAndInterests(@RequestBody SkillsAndInterestsDto updateDto) {
		ResumeDto resumeDto = skillAndInterestService.updateSkillsAndInterests(updateDto);
		return ResponseEntity.ok(resumeDto);
	}

}

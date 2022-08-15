package dislinkt.accountservice.services.impl;

import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.BiographyDto;
import dislinkt.accountservice.dtos.PhoneNumberDto;
import dislinkt.accountservice.dtos.ResumeDto;
import dislinkt.accountservice.entities.Resume;
import dislinkt.accountservice.exceptions.ResumeAlreadyExists;
import dislinkt.accountservice.exceptions.EntityNotFound;
import dislinkt.accountservice.mappers.ResumeDtoMapper;
import dislinkt.accountservice.repositories.ResumeRepository;
import dislinkt.accountservice.services.ResumeService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ResumeServiceImpl implements ResumeService {

	@Autowired
	private ResumeRepository resumeRepository;
	
	@Autowired
	private ResumeDtoMapper resumeMapper;
	
	@Autowired
	private AuthenticatedUserService authenticatedUserService;
	
	public ResumeDto createResume(Long userId) {
		Resume resume = resumeRepository.findByUserId(userId);
		if (resume != null) {
			throw new ResumeAlreadyExists("Resume already exists.");
		}
		Resume newResume = new Resume(userId);
		return resumeMapper.toDto(resumeRepository.save(newResume));
	}
	
	public ResumeDto getResumeById(Long id) {
		Optional<Resume> resumeOptional = resumeRepository.findById(id);
		if (resumeOptional.isPresent()) {
			Resume resume = resumeOptional.get();
			authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
			return resumeMapper.toDto(resume);
		}
		return null;
	}
	
	public ResumeDto getResumeByUserId(Long userId) {
		Resume resume = resumeRepository.findByUserId(userId);
		if (resume != null) {
			authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
			return resumeMapper.toDto(resume);
		}
		return null;
	}
	
	public ResumeDto updateBiography(BiographyDto biographyDto) {
		Optional<Resume> resumeOptional = resumeRepository.findById(biographyDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		resume.setBiography(biographyDto.getBiography());
		resumeRepository.save(resume);
		return resumeMapper.toDto(resume);
	}
	
	public ResumeDto updatePhoneNumber(PhoneNumberDto phoneNumberDto) {
		Optional<Resume> resumeOptional = resumeRepository.findById(phoneNumberDto.getResumeId());
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		resume.setPhoneNumber(phoneNumberDto.getPhoneNumber());
		resumeRepository.save(resume);
		return resumeMapper.toDto(resume);
	}
	
	public ResumeDto changeAccountPrivacy(Long resumeId) {
		Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		resume.setPublicAccount(!resume.getPublicAccount());
		resumeRepository.save(resume);
		return resumeMapper.toDto(resume);
	}
	
	public ResumeDto changeConnectionNotifications(Long resumeId) {
		Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);
		if (!resumeOptional.isPresent()) {
			throw new EntityNotFound("Resume not found.");
		}
		Resume resume = resumeOptional.get();
		authenticatedUserService.checkAuthenticatedUser(resume.getUserId());
		resume.setMuteConnectionNotifications(!resume.getMuteConnectionNotifications());
		resumeRepository.save(resume);
		return resumeMapper.toDto(resume);
	}
}

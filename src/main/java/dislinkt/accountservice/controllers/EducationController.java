package dislinkt.accountservice.controllers;

import dislinkt.accountservice.dtos.EducationDto;
import dislinkt.accountservice.exceptions.DateException;
import dislinkt.accountservice.services.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> retrieve(@PathVariable Long userId) {
        List<EducationDto> dto = educationService.getEducation(userId);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable Long userId, @RequestBody List<EducationDto> education) throws DateException {
        List<EducationDto> dto = educationService.updateEducation(userId, education);
        return ResponseEntity.ok(dto);
    }
}

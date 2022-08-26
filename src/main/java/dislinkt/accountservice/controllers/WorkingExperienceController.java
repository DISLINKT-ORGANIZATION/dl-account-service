package dislinkt.accountservice.controllers;

import dislinkt.accountservice.dtos.WorkingExperienceDto;
import dislinkt.accountservice.exceptions.DateException;
import dislinkt.accountservice.services.WorkingExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working-experience")
public class WorkingExperienceController {

    @Autowired
    private WorkingExperienceService workingExperienceService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> retrieve(@PathVariable Long userId) {
        List<WorkingExperienceDto> dto = workingExperienceService.getWorkingExperience(userId);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable Long userId, @RequestBody List<WorkingExperienceDto> experience) throws DateException {
        List<WorkingExperienceDto> dto = workingExperienceService.updateWorkingExperience(userId, experience);
        return ResponseEntity.ok(dto);
    }




}

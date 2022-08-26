package dislinkt.accountservice.controllers;

import dislinkt.accountservice.dtos.AccountDto;
import dislinkt.accountservice.dtos.JobPositionDto;
import dislinkt.accountservice.services.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job-positions")
public class JobPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getResumeByUserId() {
        List<JobPositionDto> dto = jobPositionService.getAllJobPositions();
        return ResponseEntity.ok(dto);
    }
}

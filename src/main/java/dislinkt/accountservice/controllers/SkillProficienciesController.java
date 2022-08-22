package dislinkt.accountservice.controllers;

import dislinkt.accountservice.dtos.SkillProficiencyDto;
import dislinkt.accountservice.services.SkillProficiencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/skill-proficiencies")
public class SkillProficienciesController {

    @Autowired
    private SkillProficiencyService skillProficiencyService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping()
    public ResponseEntity<Object> retrieveAllSkillProficiencies() {
        List<SkillProficiencyDto> dto = skillProficiencyService.getAllSkillProficiencies();
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("type")
    public ResponseEntity<Map<Integer, List<SkillProficiencyDto>>> getAllByType() {
        Map<Integer, List<SkillProficiencyDto>> dtos = skillProficiencyService.getAllByType();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/user/{userId}")
    public ResponseEntity<Object> updateProficiencies(@PathVariable Long userId, @RequestBody List<SkillProficiencyDto> skillProficiency) {
        skillProficiencyService.updateProficiencies(userId, skillProficiency);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

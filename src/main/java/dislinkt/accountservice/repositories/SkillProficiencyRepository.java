package dislinkt.accountservice.repositories;

import dislinkt.accountservice.entities.SkillProficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SkillProficiencyRepository extends JpaRepository<SkillProficiency, Long> {

    Optional<SkillProficiency> findById(Long id);
    List<SkillProficiency> findAll();
}

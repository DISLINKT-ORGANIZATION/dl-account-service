package dislinkt.accountservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.WorkingExperience;

@Repository
public interface WorkingExperienceRepository extends JpaRepository<WorkingExperience, Long> {

	Optional<WorkingExperience> findById(Long id);
	List<WorkingExperience> findAllByAccountId(Long accountId);
}

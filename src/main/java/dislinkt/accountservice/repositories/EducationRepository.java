package dislinkt.accountservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

	Optional<Education> findById(Long id);

    List<Education> findAllByAccountId(Long accountId);
}

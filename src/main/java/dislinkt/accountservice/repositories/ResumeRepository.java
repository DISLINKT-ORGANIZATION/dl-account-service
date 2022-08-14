package dislinkt.accountservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

	Optional<Resume> findById(Long id);
	Resume findByUserId(Long userId);
}

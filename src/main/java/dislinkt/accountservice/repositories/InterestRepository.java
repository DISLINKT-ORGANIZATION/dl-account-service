package dislinkt.accountservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {

	Optional<Interest> findById(Long id);
}

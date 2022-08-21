package dislinkt.accountservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findById(Long id);
	Account findByUserId(Long userId);
}

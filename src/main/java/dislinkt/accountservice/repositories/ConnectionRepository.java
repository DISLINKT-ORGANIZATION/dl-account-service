package dislinkt.accountservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

	Optional<Connection> findById(Long id);
}

package dislinkt.accountservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dislinkt.accountservice.entities.ConnectionRequest;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

	Optional<ConnectionRequest> findById(Long id);
	
	@Query("SELECT cr FROM ConnectionRequest cr WHERE cr.receiverId = ?1 AND cr.accepted = false")
	List<ConnectionRequest> findByReceiverId(Long receiverId);
	
	@Query("SELECT cr FROM ConnectionRequest cr WHERE cr.senderId = ?1 AND cr.receiverId = ?2 AND cr.accepted = false")
	List<ConnectionRequest> findBySenderAndReceiver(Long senderId, Long receiverId);
}

package dislinkt.accountservice.repositories;

import dislinkt.accountservice.entities.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

    List<JobPosition> findAll();

    JobPosition findOneByTitle(String positionTitle);
}

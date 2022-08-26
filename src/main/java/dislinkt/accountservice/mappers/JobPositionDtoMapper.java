package dislinkt.accountservice.mappers;

import dislinkt.accountservice.dtos.JobPositionDto;
import dislinkt.accountservice.entities.JobPosition;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPositionDtoMapper {

    public JobPositionDto toDto(JobPosition jobPosition) {
        return new JobPositionDto(jobPosition.getId(), jobPosition.getTitle());
    }

    public List<JobPositionDto> toCollectionDto(Collection<JobPosition> jobPositions) {
        return jobPositions.stream().map(this::toDto).collect(Collectors.toList());
    }

}

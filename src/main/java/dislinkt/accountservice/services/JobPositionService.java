package dislinkt.accountservice.services;

import dislinkt.accountservice.dtos.JobPositionDto;

import java.util.List;

public interface JobPositionService {

    List<JobPositionDto> getAllJobPositions();
}

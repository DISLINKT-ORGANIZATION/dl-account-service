package dislinkt.accountservice.services.impl;

import dislinkt.accountservice.dtos.JobPositionDto;
import dislinkt.accountservice.entities.JobPosition;
import dislinkt.accountservice.mappers.JobPositionDtoMapper;
import dislinkt.accountservice.repositories.JobPositionRepository;
import dislinkt.accountservice.services.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobPositionServiceImpl implements JobPositionService {

    @Autowired
    private JobPositionDtoMapper mapper;

    @Autowired
    private JobPositionRepository repository;

    @Override
    public List<JobPositionDto> getAllJobPositions() {
        List<JobPosition> positions = repository.findAll();
        return mapper.toCollectionDto(positions);
    }
}

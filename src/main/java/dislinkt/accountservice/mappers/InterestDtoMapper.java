package dislinkt.accountservice.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dislinkt.accountservice.dtos.InterestDto;
import dislinkt.accountservice.entities.Interest;

@Service
public class InterestDtoMapper {

	public InterestDto toDto(Interest interest) {
		return new InterestDto(interest.getId(), interest.getName());
	}

	public Interest toEntity(InterestDto dto) {
		return new Interest(dto.getName());
	}
	
	public List<InterestDto> toCollectionDto(Collection<Interest> interests) {
		return interests.stream().map(this::toDto).collect(Collectors.toList());
	}
}

package dislinkt.accountservice.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = EventKafka.class)
public class EventKafka implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private BigInteger eventId;
	
	private Date timestamp;
	
	private String content;
	
	private EventType type;
	
	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", timestamp=" + timestamp + ", content=" + content + ", type=" + type + "]";
	}
	
	public EventKafka(Date timestamp, String content, EventType type) {
		super();
		this.timestamp = timestamp;
		this.content = content;
		this.type = type;
	}
}

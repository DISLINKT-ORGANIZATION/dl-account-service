package dislinkt.accountservice.config;

import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dislinkt.accountservice.entities.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class KafkaListeners {

	private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

	@KafkaListener(topics = "dislinkt-notifications", groupId = "groupId")
	void listener(@Payload Notification data) throws JsonMappingException, JsonProcessingException {
		logger.info("Listener received " + data);
	}
}

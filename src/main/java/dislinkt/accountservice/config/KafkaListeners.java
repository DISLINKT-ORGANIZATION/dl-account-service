package dislinkt.accountservice.config;

import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import dislinkt.accountservice.dtos.KafkaNotification;
import dislinkt.accountservice.services.impl.AccountServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class KafkaListeners {

	@Autowired
	AccountServiceImpl resumeService;

	private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

	@KafkaListener(topics = "dislinkt-notifications", groupId = "groupId")
	void listener(@Payload KafkaNotification data) throws JsonMappingException, JsonProcessingException {
		logger.info("Listener received " + data);

		switch (data.getType()) {
		case REGISTERED_USER:
			resumeService.createAccount(((Integer) data.getPayload()).longValue());
			break;
		default:
		}

	}
}

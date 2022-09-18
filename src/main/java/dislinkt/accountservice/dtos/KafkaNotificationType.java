package dislinkt.accountservice.dtos;

import java.io.Serializable;

public enum KafkaNotificationType implements Serializable {
	REGISTERED_USER,
	NEW_CONNECTION,
	CONNECTION_REQUEST
}

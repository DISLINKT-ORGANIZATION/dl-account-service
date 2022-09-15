package dislinkt.accountservice.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum EventType implements Serializable {
	USER_REGISTERED(0),
	USER_LOGGED_IN(1),
	CREATED_POST(2),
	ADDED_COMMENT(3),
	CREATED_JOB_OFFER(4),
	FOLLOWED(5),
	UNFOLLOWED(6),
	SENT_FOLLOW_REQUEST(7),
	ACCEPTED_FLLOW_REQUEST(8),
	BLOCKED(9),
	UNBLOCKED(10),
	EXCEPTION(11);
	
	private int value;
	private static Map map = new HashMap<>();

	private EventType(int value) {
		this.value = value;
	}

	static {
		for (EventType eventType : EventType.values()) {
			map.put(eventType.value, eventType);
		}
	}

	public static EventType valueOfInt(int eventType) {
		return (EventType) map.get(eventType);
	}

	public int getValue() {
		return value;
	}
}

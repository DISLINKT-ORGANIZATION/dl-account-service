package dislinkt.accountservice.entities;

import java.util.HashMap;
import java.util.Map;

public enum FieldOfStudy {
	BACHELOR(0), MASTER(1), PHD(2);
	
	private int value;
	private static Map map = new HashMap<>();

	private FieldOfStudy(int value) {
		this.value = value;
	}

	static {
		for (FieldOfStudy fieldOfStudy : FieldOfStudy.values()) {
			map.put(fieldOfStudy.value, fieldOfStudy);
		}
	}

	public static FieldOfStudy valueOfInt(int fieldOfStudy) {
		return (FieldOfStudy) map.get(fieldOfStudy);
	}

	public int getValue() {
		return value;
	}
}

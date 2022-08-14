package dislinkt.accountservice.entities;

import java.util.HashMap;
import java.util.Map;

public enum SeniorityLevel {
	JUNIOR(0), MEDIOR(1), SENIOR(2);
	
	private int value;
	private static Map map = new HashMap<>();

	private SeniorityLevel(int value) {
		this.value = value;
	}

	static {
		for (SeniorityLevel seniorityLevel : SeniorityLevel.values()) {
			map.put(seniorityLevel.value, seniorityLevel);
		}
	}

	public static SeniorityLevel valueOfInt(int seniorityLevel) {
		return (SeniorityLevel) map.get(seniorityLevel);
	}

	public int getValue() {
		return value;
	}
}

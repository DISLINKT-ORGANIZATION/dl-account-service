package dislinkt.accountservice.entities;

import java.util.HashMap;
import java.util.Map;

public enum SkillType {
	PROGRAMMING_LANGUAGE(0), TECHNOLOGY(1), LANGUAGE(2), SOFT_SKILL(3);
	
	private int value;
	private static Map map = new HashMap<>();

	private SkillType(int value) {
		this.value = value;
	}

	static {
		for (SkillType skillType : SkillType.values()) {
			map.put(skillType.value, skillType);
		}
	}

	public static SkillType valueOfInt(int skillType) {
		return (SkillType) map.get(skillType);
	}

	public int getValue() {
		return value;
	}
}

package dislinkt.accountservice.entities;

import java.util.HashMap;
import java.util.Map;

public enum Proficiency {
    NONE(0),
    BASIC(1),
    GOOD(2),
    VERY_GOOD(3),
    EXCELLENT(4),
    EXPERT(5);

    private int value;
    @SuppressWarnings("rawtypes")
    private static Map map = new HashMap<>();

    Proficiency(int value) {
        this.value = value;
    }

    static {
        for (Proficiency prof : Proficiency.values()) {
            map.put(prof.value, prof);
        }
    }

    public static Proficiency valueOfInt(int prof) {
        return (Proficiency) map.get(prof);
    }

    public int getValue() {
        return value;
    }

}

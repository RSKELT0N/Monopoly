package Enumerations;

import java.util.HashMap;

public enum SquareType {
    FACTORY("Factory"),
    POWERPLANT("Power Plant"),
    OILRIG("Oil Rig"),
    EMBANKMENT("Embankment"),
    WILD("Wild"),
    ZOO("Zoo"),
    JAIL("JAIL"),
    START("Start"),
    CHANCE("Chance"),
    REPOSITORY("Repository");

    private String type;

    SquareType(String i) {
        this.type = i;
    }

    public String getType() {
        return type;
    }
}

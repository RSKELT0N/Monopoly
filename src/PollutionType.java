package Enumerations;

public enum PollutionType {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private String type;
    private static final int size = 3;

    PollutionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static int size() {
        return size;
    }
}

package Enumerations;

public enum Ownership {
    PURCHASED("PURCHASED"),
    UNPURCHASED("UN-PURCHASED"),
    PUBLIC("PUBLIC");

    private String state;

    Ownership(String state) {
        this.state = state;
    }

    public String getType() {
        return state;
    }
}

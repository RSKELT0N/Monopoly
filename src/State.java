package Enumerations;

public enum State {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    String state;

    State(String str) {
        this.state = str;
    }

    public String getState() {
        return state;
    }
}

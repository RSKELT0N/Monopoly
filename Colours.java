package Enumerations;

public enum Colours {
    BLUE("Blue"),
    PURPLE("Purple"),
    YELLOW("Yellow"),
    GREEN("Green"),
    RED("Red"),
    BROWN("Brown"),
    WHITE("White");

    private String col;

    Colours(String num) {
        this.col = num;
    }
    public String getColour() {
        return col;
    }
}
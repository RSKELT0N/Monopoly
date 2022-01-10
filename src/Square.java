package Game;

import Enumerations.Colours;
import Enumerations.Ownership;
import Enumerations.PollutionType;
import Enumerations.SquareType;
import Interfaces.ISquare;

public class    Square implements ISquare {
    private String name;
    private Colours colour;
    private double price;
    private SquareType squareType;
    private PollutionType pollutionType;
    private Ownership ownership;
    private int storedAnimals;
    private final int maxStoredAnimals = 150;

    Square(Colours colour,SquareType type) {
        this.colour = colour;
        this.squareType = type;
        if(squareType == SquareType.WILD)
            this.storedAnimals = 20;
        setPollutionType();
        setOwnership();
        setPrice();
    }

    public PollutionType getPollutionType() {
        return pollutionType;
    }

    @Override
    public String toString() {
        return "\nColour: " + this.colour.getColour() +
                "\nPrice: " + (int)this.price + " resources\nStructure: " +
                squareType.getType() + "\n" + "Pollution: " +
                pollutionType.getType() + "\n"
                +"OwnerShip: "+ownership.getType()+"\n";
    }

    public void setPollutionType() {
        if (squareType == SquareType.WILD || squareType == SquareType.START)
            this.pollutionType = PollutionType.LOW;
        else this.pollutionType = PollutionType.HIGH;
    }

    public void setPrice() {

        switch (squareType) {
            case FACTORY: this.price = 400; break;
            case OILRIG: this.price = 500; break;
            case POWERPLANT: this.price = 550; break;
            case EMBANKMENT: this.price = 300; break;
            default: this.price = 0; break;
        }
    }

    @Override
    public int findDifference() {
        int diff = this.maxStoredAnimals - this.storedAnimals;
        return diff;
    }

    public void setOwnership(Ownership ownership) {
        this.ownership = ownership;
    }

    public void setOwnership() {
        if(squareType == SquareType.WILD || squareType == SquareType.ZOO || squareType == SquareType.JAIL || squareType == SquareType.CHANCE || squareType == SquareType.START)
            ownership = Ownership.PUBLIC;
        else ownership = Ownership.UNPURCHASED;
    }

    public void setPollutionType(PollutionType type) {
        this.pollutionType = type;
    }

    public void setStoredAnimals(int i) {
        this.storedAnimals += i;
    }

    public int getStoredAnimals() {
        return storedAnimals;
    }

    public void setSquareType(SquareType squareType) {
        this.squareType = squareType;
    }

    public Ownership getOwnership() {
        return ownership;
    }

    public SquareType getSquareType() {
        return squareType;
    }

    public double getPrice() {
        return price;
    }
}

package Interfaces;

import Enumerations.PollutionType;
import Enumerations.SquareType;

public interface ISquare {
    public String toString();
    public PollutionType getPollutionType();
    public void setPollutionType();
    public void setPollutionType(PollutionType type);
    public void setStoredAnimals(int i);
    public int getStoredAnimals();
    public void setSquareType(SquareType squareType);
    public void setPrice();
    public int findDifference();

}

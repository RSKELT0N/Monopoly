package Game;

import java.io.FileNotFoundException;

public class Player {

    private String name;
    private int id;
    private double resources;
    private int animals;
    private Node position;
    private boolean skipTurn;

    public Player(String name, double resources,int id) throws FileNotFoundException {
        this.name = name;
        this.id = id;
        this.resources = resources;
        this.position = Game.getInstance().getSquares().get(0);
        setAnimals(500);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return resources;
    }

    public Node getPosition() {
        return position;
    }

    public void setPosition(Node position) {
        this.position = position;
    }

    public double getResources() {
        return resources;
    }

    public void setResources(double resources) {
        this.resources += resources;
    }

    public int getAnimals() {
        return animals;
    }

    public void setAnimals() {
        this.animals = 500;
    }
    public void setAnimals(int i) {
        this.animals += i;
    }

    public int getId() {
        return id;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }
}

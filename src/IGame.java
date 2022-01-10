package Interfaces;

import Game.Game;
import Game.Node;
import Game.Player;

import java.io.FileNotFoundException;

public interface IGame {
    public void initialiseSquares() throws FileNotFoundException;
    public void initialisePlayers() throws FileNotFoundException;
    public String getName();
    public Player[] getPlayers();
    public Player getWinner();
    public Player getPlayersTurn();
    public void setAmountOfPlayers(int i) throws FileNotFoundException;
    public int getAmountOfPlayers();
    public int[] throwDice();
    public void generateOptions(Node node, int player) throws Exception;
    public int randomAnimalAmountFromWild(Node node) throws FileNotFoundException;
    public void determineOption(Node node,int player, int type, int choice,int data) throws Exception;
    public boolean buySquare(Node node, Player player);
    public boolean checkOwnership(Node node, Player player);
    public void initialiseOwnership();
    public void addSquareToLinkedList(String colour,String structure);
    public int randomAnimalsForZoo();
    public void payRent(Node node, Player player) throws Exception;

    public static Game getInstance() {
        return null;
    }
}

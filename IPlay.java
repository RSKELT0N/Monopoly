package Interfaces;

import Game.Player;

import java.io.FileNotFoundException;

public interface IPlay {
    void run() throws Exception;
    public void initialiseGame() throws FileNotFoundException;
    public void playersTurn(int i) throws Exception;
    public boolean checkJail(int i) throws Exception;
    public int throwDice(int i) throws FileNotFoundException;
    public String printTurnInfo(Player player);
}

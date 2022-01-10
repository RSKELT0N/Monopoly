package Interfaces;


import Game.Node;
import Game.Player;

import java.io.FileNotFoundException;

public interface IChanceCard {
    public void randomChance(Node node, Player player) throws Exception;
    public void increaseResourcesChance(Node node, Player player) throws FileNotFoundException;
    public void increaseAnimalsChance(Node node, Player player) throws FileNotFoundException;
    public void deleteRandomNodeChance(Node node, Player player);
    public void sentToJailChance(Node node, Player player) throws Exception;
    public void randomPropertyChance(Node node, Player player) throws FileNotFoundException;
}

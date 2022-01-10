package Interfaces;

import Game.Node;
import Game.SquareLinkedList;
import java.io.FileNotFoundException;

public interface ISquareLinkedList {

    public SquareLinkedList add(Node node);
    public String toString();
    public int size();
    public Node get(int i);
    public Node jumpPos(int diceThrow, int playerPos) throws FileNotFoundException;
    public Node findJail() throws Exception;
    public void deleteNode(Node node);
}

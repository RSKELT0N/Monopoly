package Game;

import Enumerations.SquareType;
import Interfaces.ISquareLinkedList;

import java.io.FileNotFoundException;


public class SquareLinkedList implements ISquareLinkedList {
    private Node first;
    private Node last;
    private int count;

    SquareLinkedList() {
        this.first = null;
        this.last = null;
        this.count = 0;
    }

    @Override
    public SquareLinkedList add(Node node) {
        Node data = node;
        data.setPlace(count);
        if(data == null)
            return this;
        if(first == null) {
            first = last = data;
        } else {
            last.prev = last;
            last.next = data;
            last = data;
        }
        count++;
        return this;
    }

    @Override
    public String toString() {
        String res = "";
        Node data = first;
        int i = 1;
        while(data != null) {
            res+="("+i+") "+data.data.toString();
            res+="\n";
            data = data.next;
            i++;
        }
        return res;
    }

    @Override
    public Node get(int i) {
        Node node = first;
        int count = 0;
        while(node != null) {
            if(count == i)
                break;
            node = node.next;
            count++;
        }
        return node;
    }

    @Override
    public Node jumpPos(int diceThrow, int player) throws FileNotFoundException {
        int pos = Game.getInstance().getPlayers()[player].getPosition().getPlace() + diceThrow;
        if(pos >= size())
            pos = pos % size();

        Game.getInstance().getPlayers()[player].setPosition(get(pos));
        return get(pos);
    }

    @Override
    public Node findJail() throws Exception {
        Node node = first;
        while(node != null) {
            if (node.getData().getSquareType() == SquareType.JAIL)
                return node;
            node = node.next;
        }
        throw new Exception("No Jail Valid");
    }

    @Override
    public void deleteNode(Node node) {
        Node listNode = first;
        while(listNode != null)
            if(listNode == node)
                listNode = listNode.next.next.next;
            else listNode = listNode.next;
    }

    @Override
    public int size() {
        return count;
    }
}

package Game;

public class Node {
    Square data;
    Player owner;
    Node next;
    Node prev;
    int place;

    Node(Square data) {
        this.data = data;
        this.next = null;
        this.prev = null;
        this.place = 0;
    }

    public Square getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
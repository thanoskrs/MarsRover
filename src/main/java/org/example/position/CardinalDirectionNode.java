package org.example.position;

/**
 * Node that contains a specific cardinal direction, a pointer to the previous one
 * and to the next one, clockwise.
 */
public class CardinalDirectionNode {

    private CardinalDirectionNode prev;
    private String current;
    private CardinalDirectionNode next;

    public CardinalDirectionNode(String current) {
        this.current = current;
    }

    public CardinalDirectionNode getPrev() {
        return prev;
    }

    public void setPrev(CardinalDirectionNode prev) {
        this.prev = prev;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public CardinalDirectionNode getNext() {
        return next;
    }

    public void setNext(CardinalDirectionNode next) {
        this.next = next;
    }
}

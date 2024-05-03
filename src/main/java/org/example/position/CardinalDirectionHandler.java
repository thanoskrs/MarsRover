package org.example.position;

/**
 * Initializes the four cardinal directions using {@link CardinalDirectionNode} and
 * sets the previous and next direction for each one, clockwise.
 */
public class CardinalDirectionHandler {

    public static final CardinalDirectionNode north = new CardinalDirectionNode("N");
    public static final CardinalDirectionNode east = new CardinalDirectionNode("E");
    public static final CardinalDirectionNode south = new CardinalDirectionNode("S");
    public static final CardinalDirectionNode west = new CardinalDirectionNode("W");

    static {
        north.setPrev(west);
        north.setNext(east);

        east.setPrev(north);
        east.setNext(south);

        south.setPrev(east);
        south.setNext(west);

        west.setPrev(south);
        west.setNext(north);
    }
}

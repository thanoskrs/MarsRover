package org.example.position;

import org.example.exception.InvalidCardinalDirectionException;

/**
 * Keeps tracking of the rover's current coordinates in the 2D space.
 */
public class RoverPosition {

    private int x;
    private int y;
    private CardinalDirectionNode cardinalDirection;

    public RoverPosition(int x, int y, String cardinalDirection) throws InvalidCardinalDirectionException {
        this.x = x;
        this.y = y;

        setCurrentCardinalDirection(cardinalDirection);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void increaseX() {
        x++;
    }

    public void decreaseX() {
        x--;
    }

    public void increaseY() {
        y++;
    }

    public void decreaseY() {
        y--;
    }

    /**
     * Sets the {@link RoverPosition#cardinalDirection} to the specified one.
     *
     * @param currentCardinalDirection The rover's cardinal direction.
     * @throws InvalidCardinalDirectionException If the cardinal direction given is invalid.
     */
    public void setCurrentCardinalDirection(String currentCardinalDirection) throws InvalidCardinalDirectionException {
        switch (currentCardinalDirection) {
            case "N" -> cardinalDirection = CardinalDirectionHandler.north;
            case "E" -> cardinalDirection = CardinalDirectionHandler.east;
            case "S" -> cardinalDirection = CardinalDirectionHandler.south;
            case "W" -> cardinalDirection = CardinalDirectionHandler.west;
            default -> throw new InvalidCardinalDirectionException(
                    "Invalid cardinal direction given: " + currentCardinalDirection);
        }
    }

    public void turnRight() {
        cardinalDirection = cardinalDirection.getNext();
    }

    public void turnLeft() {
        cardinalDirection = cardinalDirection.getPrev();
    }

    public String getCurrentCardinalDirection() {
        return cardinalDirection.getCurrent();
    }

    @Override
    public String toString() {
        return x + " " + y + " " + cardinalDirection.getCurrent();
    }
}

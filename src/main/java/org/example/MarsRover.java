package org.example;

import org.example.exception.ErrorMessageUtils;
import org.example.exception.InvalidCardinalDirectionException;
import org.example.exception.InvalidMoveException;
import org.example.exception.OutOfBoundsException;
import org.example.position.RoverPosition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarsRover {

    public static int boundX;
    public static int boundY;

    public static void main(String[] args) throws InvalidCardinalDirectionException, IOException, OutOfBoundsException, InvalidMoveException {

        processRoverData("src/main/resources/input/rover-info.txt")
                .forEach(System.out::println);

    }

    /**
     * Calculate the final position of each rover.
     * Read the txt with the given file name in order to get the rovers' data.
     * The first row indicates the upper right corner of the rectangular space, at which a rover can move in.
     * The remaining lines, 2 by 2, give information about a rover's current position and the moves to be made.
     *
     * @param fileName  The file name of the txt with rovers' data.
     * @return  A {@link List<String>} containing the last position of each rover.
     * @throws InvalidCardinalDirectionException If rover's cardinal direction is not 'N', 'E', 'S' or 'W'.
     * @throws OutOfBoundsException If a rover moves out of the rectangular.
     * @throws InvalidMoveException If a rover's move is not 'M', 'R' or 'L'.
     * @throws IOException  If an error occurs with the txt (e.g. does not exist, wrong file format).
     */
    public static List<String> processRoverData(String fileName)
            throws InvalidCardinalDirectionException, OutOfBoundsException, InvalidMoveException, IOException {
        List<String> roversFinalPosition = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String upperRightBoundaries = br.readLine();
            processUpperRightBoundaries(upperRightBoundaries);

            String line;
            while ((line = br.readLine()) != null) {
                // calculate the final position of each rover
                String roverCurrentPosition = line;
                String roverMoves = br.readLine();

                if (roverMoves == null) {
                    throw new IOException(ErrorMessageUtils.ERROR_NO_MOVES_FOR_ROVER + roverCurrentPosition);
                }

                String currentRoverFinalPos = getFinalPosition(roverCurrentPosition, roverMoves);
                roversFinalPosition.add(currentRoverFinalPos);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file: " + e.getMessage());
        }

        return roversFinalPosition;
    }

    /**
     * Process and init the upper right boundaries of the rectangular
     *
     * @param upperRightBoundaries The upper right boundaries.
     * @throws IOException  If the input is null or has invalid format.
     */
    private static void processUpperRightBoundaries(String upperRightBoundaries) throws IOException {
        if (upperRightBoundaries == null) {
            throw new IOException(ErrorMessageUtils.ERROR_NO_BOUNDARIES_PROVIDED);
        }

        String[] boundaries = upperRightBoundaries.split(" ");
        if (boundaries.length != 2) {
            throw new IOException(ErrorMessageUtils.ERROR_INVALID_BOUNDARIES_LENGTH + boundaries.length);
        }

        boundX = Integer.parseInt(boundaries[0]);
        boundY = Integer.parseInt(boundaries[1]);
    }

    /**
     * Initialize the current {@link RoverPosition} based on the given input ant moves according the
     * specified moves. Calculate and return the final position of the rover.
     *
     * @param roverCurrentPosition Τhe current rover's coordinates and cardinal direction.
     * @param roverMoves The desired moves that the rover will make.
     * @return The final rover position.
     * @throws OutOfBoundsException If the rover moves out the given space.
     * @throws InvalidMoveException If any invalid move symbol is given.
     * @throws InvalidCardinalDirectionException If any invalid cardinal direction is given.
     */
    public static String getFinalPosition(String roverCurrentPosition, String roverMoves)
            throws OutOfBoundsException, InvalidMoveException, InvalidCardinalDirectionException {

        List<String> roverCurrentPositionList = Arrays.stream(roverCurrentPosition.split(" ")).toList();

        RoverPosition roverPosition = new RoverPosition(
                Integer.parseInt(roverCurrentPositionList.get(0)),
                Integer.parseInt(roverCurrentPositionList.get(1)),
                roverCurrentPositionList.get(2));

        checkRoverPosition(roverPosition);

        for (char c : roverMoves.toCharArray()) {
            switch (c) {
                case 'L' -> roverPosition.turnLeft();
                case 'R' -> roverPosition.turnRight();
                case 'M' -> decideNextMove(roverPosition);
                default -> throw new InvalidMoveException(ErrorMessageUtils.ERROR_INVALID_MOVE + c);
            }
        }

        return roverPosition.toString();
    }

    /**
     * Decide the next move of the rover at the 2D dimensional space, based on the current
     * rover's ordinal direction.
     * If the next move exceeds the given rectangular space, then throws {@link OutOfBoundsException}.
     *
     * @param roverPosition The current {@link RoverPosition roverPosition} of the rover.
     * @throws OutOfBoundsException If the rover moves out the given space.
     */
    private static void decideNextMove(RoverPosition roverPosition)
            throws OutOfBoundsException {

        switch (roverPosition.getCurrentCardinalDirection()) {
            case "N" -> roverPosition.increaseY();
            case "E" -> roverPosition.increaseX();
            case "S" -> roverPosition.decreaseY();
            case "W" -> roverPosition.decreaseX();
        }

        checkRoverPosition(roverPosition);
    }

    /**
     * Validate that the rover is inside the given rectangular.
     *
     * @param roverPosition The rover's position.
     * @throws OutOfBoundsException If rover exceeds the bounds.
     */
    private static void checkRoverPosition(RoverPosition roverPosition) throws OutOfBoundsException {
        if (roverPosition.getX() < 0 || roverPosition.getX() > boundX
                || roverPosition.getY() < 0 ||roverPosition.getY() > boundY) {
            throw new OutOfBoundsException();
        }
    }

}
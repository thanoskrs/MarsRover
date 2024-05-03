package org.example;

import org.example.exception.ErrorMessageUtils;
import org.example.exception.InvalidCardinalDirectionException;
import org.example.exception.InvalidMoveException;
import org.example.exception.OutOfBoundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class MarsRoverTest {

    private static final String TEST_RESOURCES_PATH = "src/test/resources/input/";

    @BeforeEach
    public void initBounds() {
        MarsRover.boundX = 5;
        MarsRover.boundY = 5;
    }

    @Test
    public void testSuccess() throws InvalidCardinalDirectionException, OutOfBoundsException, InvalidMoveException, IOException {
        Assertions.assertEquals(
                List.of("1 3 N", "5 1 E"),
                MarsRover.processRoverData(TEST_RESOURCES_PATH + "rover-info.txt"));
    }

    @Test
    public void testNoBoundariesProvidedFail() {
        IOException ioException = Assertions.assertThrows(
                IOException.class,
                () -> MarsRover.processRoverData(TEST_RESOURCES_PATH + "rover-info-empty.txt"));

        Assertions.assertTrue(ioException.getMessage().contains(ErrorMessageUtils.ERROR_NO_BOUNDARIES_PROVIDED));
    }

    @Test
    public void testInvalidBoundariesFail() {
        IOException ioException = Assertions.assertThrows(
                IOException.class,
                () -> MarsRover.processRoverData(TEST_RESOURCES_PATH + "rover-info-invalid-boundaries.txt"));

        Assertions.assertTrue(ioException.getMessage().contains(ErrorMessageUtils.ERROR_INVALID_BOUNDARIES_LENGTH));
    }

    @Test
    public void testNoMovesForRoverProvidedFail() throws InvalidCardinalDirectionException, OutOfBoundsException, InvalidMoveException, IOException {
        IOException ioException = Assertions.assertThrows(
                IOException.class,
                () -> MarsRover.processRoverData(TEST_RESOURCES_PATH + "rover-info-no-moves-for-rover.txt"));

        Assertions.assertTrue(ioException.getMessage().contains(ErrorMessageUtils.ERROR_NO_MOVES_FOR_ROVER));
    }

    @Test
    public void testRover1outOfBoundsFail() {
        Assertions.assertThrows(
                OutOfBoundsException.class,
                () -> MarsRover.getFinalPosition("5 5 N", "M"));
    }

    @Test
    public void testRover2outOfBoundsFail() {
        Assertions.assertThrows(
                OutOfBoundsException.class,
                () -> MarsRover.getFinalPosition("0 0 N", "RRM"));
    }

    @Test
    public void testRoverInvalidMoveFail() {
        Assertions.assertThrows(
                InvalidMoveException.class,
                () -> MarsRover.getFinalPosition("3 3 E", "MMP"));
    }

    @Test
    public void testRoverInvalidCardinalDirectionFail() {
        Assertions.assertThrows(
                InvalidCardinalDirectionException.class,
                () -> MarsRover.getFinalPosition("3 3 A", "MM"));
    }

}

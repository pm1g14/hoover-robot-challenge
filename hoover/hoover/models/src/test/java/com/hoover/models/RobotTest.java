package com.hoover.models;

import com.hoover.models.emums.Instructions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotTest {

    @Test
    public void testMoveEast() {
        // Given
        Robot robot = getRobot.get();
        // When
        robot.moveEast();
        // Then
        assertEquals(robot.getCurrPosition(), new GridPosition(6, 5, false));
    }

    @Test
    public void testMoveWest() {
        // Given
        Robot robot = getRobot.get();
        // When
        robot.moveWest();
        // Then
        assertEquals(robot.getCurrPosition(), new GridPosition(4, 5, false));
    }

    @Test
    public void testMoveSouth() {
        // Given
        Robot robot = getRobot.get();
        // When
        robot.moveSouth();
        // Then
        assertEquals(robot.getCurrPosition(), new GridPosition(5, 4, false));
    }

    @Test
    public void testMoveNorth() {
        // Given
        Robot robot = getRobot.get();
        // When
        robot.moveNorth();
        // Then
        assertEquals(robot.getCurrPosition(), new GridPosition(5, 6, false));
    }

    @Test
    public void testGetNewPosition() {
        Robot robot = getRobot.get();
        robot.getNewPosition(Instructions.NORTH);
        assertEquals(robot.getCurrPosition(), new GridPosition(5, 6, false));
        robot.getNewPosition(Instructions.SOUTH);
        assertEquals(robot.getCurrPosition(), new GridPosition(5, 5, false));
        robot.getNewPosition(Instructions.EAST);
        assertEquals(robot.getCurrPosition(), new GridPosition(6, 5, false));
        robot.getNewPosition(Instructions.WEST);
        assertEquals(robot.getCurrPosition(), new GridPosition(5, 5, false));
    }

    private Supplier<Robot> getRobot = () ->
            new Robot(
                new GridPosition(5, 5, false),
                new RoomGrid(List.of(
                        new GridPosition(5, 5, false)), 10, 10,
                        List.of(
                                new GridPosition(10, 10, false)
                        )
                )
        );

}

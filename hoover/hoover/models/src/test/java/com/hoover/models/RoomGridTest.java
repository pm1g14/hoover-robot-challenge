package com.hoover.models;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomGridTest {

    @Test
    public void testGetPatch() {
        // Given
        RoomGrid roomGrid = new RoomGrid(new LinkedList<>(), 5, 5, List.of(new GridPosition(1, 2, true), new GridPosition(1, 3, true)));
        // Then
        assertTrue(roomGrid.isWithinBounds(1, 2));
        assertFalse(roomGrid.isWithinBounds(10, 20));
    }

    @Test
    public void testIsPreviouslyVisitedShouldReturnFalse() {
        // Given
        RoomGrid roomGrid = new RoomGrid(new LinkedList<>(), 5, 5, List.of(new GridPosition(1, 2, true), new GridPosition(1, 3, true)));
        // Then
        assertFalse(RoomGrid.isPreviouslyVisited.test(roomGrid.visitedPositions(), new GridPosition(1, 2, true)));
    }

    @Test
    public void testIsPreviouslyVisitedShouldReturnTrue() {
        // Given
        LinkedList<GridPosition> visitedPositions = new LinkedList<>();
        visitedPositions.add(new GridPosition(1, 2, true));
        visitedPositions.add(new GridPosition(1, 3, true));
        visitedPositions.add(new GridPosition(1, 4, true));
        RoomGrid roomGrid = new RoomGrid(visitedPositions, 5, 5, List.of(new GridPosition(1, 2, true), new GridPosition(1, 3, true)));
        // Then
        assertTrue(RoomGrid.isPreviouslyVisited.test(roomGrid.visitedPositions(), new GridPosition(1, 2, true)));
    }

    @Test
    public void testIsDirtyAndNotVisited() {
        // Given
        LinkedList<GridPosition> visitedPositions = new LinkedList<>();
        visitedPositions.add(new GridPosition(1, 2, true));
        visitedPositions.add(new GridPosition(1, 3, true));
        visitedPositions.add(new GridPosition(1, 4, true));
        // Then
        assertFalse(RoomGrid.isDirtyAndNotVisited.test(visitedPositions, new GridPosition(1, 4, true)));
        assertTrue(RoomGrid.isDirtyAndNotVisited.test(visitedPositions, new GridPosition(1, 5, true)));
    }
}

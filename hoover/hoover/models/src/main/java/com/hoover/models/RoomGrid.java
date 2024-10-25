package com.hoover.models;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

public record RoomGrid(List<GridPosition> visitedPositions, int dimensionX, int dimensionY, List<GridPosition> dirtyPatches) {

    public List<GridPosition> addVisitedPosition(GridPosition position) {
        if (Objects.isNull(position)) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        visitedPositions.add(position);
        return Collections.unmodifiableList(visitedPositions);
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x <= dimensionX && y >= 0 && y <= dimensionY;
    }
    public static final BiPredicate<List<GridPosition>, GridPosition> isPreviouslyVisited = (List<GridPosition> visited, GridPosition newPosition) -> visited.stream().anyMatch(p -> p.coordX() == newPosition.coordX() && p.coordY() == newPosition.coordY());

    public static final BiPredicate<List<GridPosition>, GridPosition> isDirtyAndNotVisited = (visited, position) -> position.isDirty() && (!isPreviouslyVisited.test(visited, position));
}

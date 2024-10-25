package com.hoover.models;

import com.hoover.models.emums.Instructions;

public class Robot {

    private GridPosition currPosition;
    private final RoomGrid gridToMove;

    public Robot(GridPosition startPosition, RoomGrid gridToMove) {
        this.currPosition = startPosition;
        this.gridToMove = gridToMove;
    }

    public synchronized GridPosition getCurrPosition() {
        return currPosition;
    }


    public synchronized GridPosition getNewPosition(Instructions instruction) {
        GridPosition position = switch (instruction) {
            case NORTH -> moveNorth();
            case SOUTH -> moveSouth();
            case EAST -> moveEast();
            case WEST -> moveWest();
        };

        return position;
    }

    protected GridPosition moveNorth() {
        int newY = currPosition.coordY() + 1;

        GridPosition newPosition = new GridPosition(
                currPosition.coordX(),
                newY,
                gridToMove.dirtyPatches().stream().anyMatch(p -> p.coordX() == currPosition.coordX() && p.coordY() == newY));

        if (!gridToMove.isWithinBounds(newPosition.coordX(), newPosition.coordY())) {
            return currPosition;
        }
        currPosition = newPosition;
        return currPosition;
    }

    protected GridPosition moveSouth() {
        int newY = currPosition.coordY() - 1;

        GridPosition newPosition = new GridPosition(
                currPosition.coordX(),
                newY,
                gridToMove.dirtyPatches().stream().anyMatch(p -> p.coordX() == currPosition.coordX() && p.coordY() == newY));
        if (!gridToMove.isWithinBounds(newPosition.coordX(), newPosition.coordY())) {
            return currPosition;
        }
        currPosition = newPosition;
        return currPosition;
    }

    protected GridPosition moveEast() {
        int newX = currPosition.coordX() + 1;

        GridPosition newPosition = new GridPosition(
                newX,
                currPosition.coordY(),
                gridToMove.dirtyPatches().stream().anyMatch(p -> p.coordX() == newX && p.coordY() == currPosition.coordY())
        );

        if (!gridToMove.isWithinBounds(newPosition.coordX(), newPosition.coordY())) {
            return currPosition;
        }
        currPosition = newPosition;
        return currPosition;
    }

    protected GridPosition moveWest() {
        int newX = currPosition.coordX() - 1;

        GridPosition newPosition = new GridPosition(
                newX,
                currPosition.coordY(),
                gridToMove.dirtyPatches().stream().anyMatch(p -> p.coordX() == newX && p.coordY() == currPosition.coordY())
        );
        if (!gridToMove.isWithinBounds(newPosition.coordX(), newPosition.coordY())) {
            return currPosition;
        }
        currPosition = newPosition;
        return currPosition;
    }
}

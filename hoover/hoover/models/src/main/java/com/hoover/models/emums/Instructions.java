package com.hoover.models.emums;

import java.util.List;

public enum Instructions {
    NORTH('N'), SOUTH('S'), EAST('E'), WEST('W');

    private final char instruction;

    Instructions(char instruction) {
        this.instruction = instruction;
    }

    public char getInstruction() {
        return instruction;
    }

    public static char toChar(Instructions instruction) {
        return switch (instruction) {
            case NORTH -> 'N';
            case SOUTH -> 'S';
            case EAST -> 'E';
            case WEST -> 'W';
        };
    }

    public static List<Character> charValues() {
        return List.of('N', 'S', 'E', 'W');
    }

    public static Instructions fromChar(char instruction) {
        return switch (instruction) {
            case 'N' -> NORTH;
            case 'S' -> SOUTH;
            case 'E' -> EAST;
            case 'W' -> WEST;
            default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
        };
    }
}

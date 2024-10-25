package com.hoover.application.services.impl;

import com.hoover.application.dto.CoordPairDto;
import com.hoover.application.dto.RobotInputDto;
import com.hoover.application.dto.RobotOutputDto;
import com.hoover.application.services.RobotMoveService;
import com.hoover.models.emums.Instructions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ServiceApplication.class)
public class RobotMoveServiceImplTest {

    @Autowired
    private RobotMoveServiceImpl service;

    @Test
    public void testMoveAndCleanShouldReturnCurrentPositionForNullInstructions() {
        CoordPairDto roomSize = new CoordPairDto(10, 10);
        CoordPairDto start = new CoordPairDto(5, 5);
        List<CoordPairDto> patches = List.of(new CoordPairDto(5, 5), new CoordPairDto(6, 6));
        RobotInputDto inputDto = new RobotInputDto(roomSize, start, patches, null);
        RobotOutputDto result = service.moveAndClean(inputDto);
        assert result.coords().x() == start.x();
        assert result.coords().y() == start.y();
    }

    @ParameterizedTest
    @MethodSource("passInstructions")
    public void testMoveAndCleanShouldReturnFinalPosition(Instructions[] instructions, CoordPairDto expected) {
        CoordPairDto roomSize = new CoordPairDto(10, 10);
        CoordPairDto start = new CoordPairDto(5, 5);
        List<CoordPairDto> patches = List.of(new CoordPairDto(5, 5), new CoordPairDto(6, 6));
        RobotInputDto inputDto = new RobotInputDto(roomSize, start, patches, instructions);
        RobotOutputDto result = service.moveAndClean(inputDto);
        assert result.coords().x() == expected.x();
        assert result.coords().y() == expected.y();
    }

    @Test
    public void testIsDirtyPatch() {
        assertTrue(service.isDirtyPatch.test(List.of(new CoordPairDto(5, 5), new CoordPairDto(6, 6)), new CoordPairDto(5, 5)));
        assertFalse(service.isDirtyPatch.test(List.of(new CoordPairDto(5, 5), new CoordPairDto(6, 6)), new CoordPairDto(5, 6)));
        assertFalse(service.isDirtyPatch.test(List.of(), new CoordPairDto(5, 5)));
    }

    @Test
    public void testMaybeGetRoomGrid() {
        CoordPairDto roomSize = new CoordPairDto(10, 10);
        CoordPairDto start = new CoordPairDto(5, 5);
        List<CoordPairDto> patches = List.of(new CoordPairDto(5, 5), new CoordPairDto(6, 6));
        RobotInputDto inputDto = new RobotInputDto(roomSize, start, patches, null);
        assertTrue(service.maybeGetRoomGrid.apply(inputDto).isPresent());
    }

    @Test
    public void testMaybeGetRoomGridShouldReturnEmptyForMissingInput() {
        assertFalse(service.maybeGetRoomGrid.apply(null).isPresent());
    }

    static Stream<Arguments> passInstructions() {
        return Stream.of(
                Arguments.of(new Instructions[]{Instructions.NORTH, Instructions.SOUTH, Instructions.WEST, Instructions.EAST}, new CoordPairDto(5, 5)),
                Arguments.of(new Instructions[]{Instructions.NORTH, Instructions.NORTH, Instructions.NORTH, Instructions.EAST, Instructions.NORTH}, new CoordPairDto(6, 9)),
                Arguments.of(new Instructions[]{Instructions.NORTH}, new CoordPairDto(5, 6)),
                Arguments.of(new Instructions[]{Instructions.SOUTH}, new CoordPairDto(5, 4)),
                Arguments.of(new Instructions[]{Instructions.EAST}, new CoordPairDto(6, 5)),
                Arguments.of(new Instructions[]{Instructions.WEST}, new CoordPairDto(4, 5)),
                Arguments.of(new Instructions[]{Instructions.WEST, Instructions.WEST, Instructions.WEST, Instructions.WEST, Instructions.WEST, Instructions.WEST}, new CoordPairDto(0, 5))
        );
    }
}

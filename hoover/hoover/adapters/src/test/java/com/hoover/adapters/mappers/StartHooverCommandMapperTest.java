package com.hoover.adapters.mappers;

import com.hoover.adapters.http.inbound.commands.Command;
import com.hoover.adapters.http.inbound.commands.StartHooverCommand;
import com.hoover.application.dto.RobotInputDto;
import com.hoover.models.emums.Instructions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartHooverCommandMapperTest {

    @Test
    public void testMapToDto() {
        Command command = new StartHooverCommand(new int[]{5, 5}, new int[]{2, 2}, List.of(new Integer[][]{new Integer[]{1, 2}}), "NNESEESWNWW");
        CommandMapper<RobotInputDto> commandMapper = new StartHooverCommandMapper();
        RobotInputDto inputDto = commandMapper.fromDto(command);
        Instructions[] expectedInstructions = new Instructions[]{Instructions.NORTH, Instructions.NORTH, Instructions.EAST, Instructions.SOUTH, Instructions.EAST, Instructions.EAST, Instructions.SOUTH, Instructions.WEST, Instructions.NORTH, Instructions.WEST, Instructions.WEST};
        assert inputDto != null;
        assertEquals(5, inputDto.roomSize().x());
        assertEquals(5, inputDto.roomSize().y());
        assertEquals(2, inputDto.coords().x());
        assertEquals(2, inputDto.coords().y());
        assertEquals(1, inputDto.patches().size());
        assertEquals(expectedInstructions.length, inputDto.instructions().length);
    }
}

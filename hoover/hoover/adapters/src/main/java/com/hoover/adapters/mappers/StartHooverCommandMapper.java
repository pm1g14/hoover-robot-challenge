package com.hoover.adapters.mappers;

import com.hoover.adapters.http.inbound.commands.Command;
import com.hoover.adapters.http.inbound.commands.StartHooverCommand;
import com.hoover.application.dto.CoordPairDto;
import com.hoover.application.dto.RobotInputDto;
import com.hoover.models.emums.Instructions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

@Component
public class StartHooverCommandMapper implements CommandMapper<RobotInputDto> {

    @Override
    public RobotInputDto fromDto(Command command) {
        if (Objects.isNull(command)) {
            return null;
        }
        StartHooverCommand startHooverCommand = (StartHooverCommand) command;
        CoordPairDto roomSize = new CoordPairDto(startHooverCommand.roomSize()[0], startHooverCommand.roomSize()[1]);
        List<CoordPairDto> patchesOfDirt = toCoordPairDto.apply(startHooverCommand.patches());

        CoordPairDto initialHooverPosition = new CoordPairDto(startHooverCommand.coords()[0], startHooverCommand.coords()[1]);
        Instructions[] instructionsArray = toInstructions.apply(startHooverCommand.instructions());

        return new RobotInputDto(roomSize, initialHooverPosition, patchesOfDirt, instructionsArray);
    }

    @Override
    public Command toDto(RobotInputDto dto) {
        return null;
    }

    private final Function<List<Integer[]>, List<CoordPairDto>> toCoordPairDto = coords ->
            coords.stream()
            .map(p -> new CoordPairDto(p[0], p[1]))
            .toList();

    private final Function<String, Instructions[]> toInstructions = instructionsStr -> {
        char[] instructions = instructionsStr.toCharArray();
        Character[] transformed = IntStream.range(0, instructions.length)
                .mapToObj(i -> instructions[i])
                .toArray(Character[]::new);

        return Arrays.stream(transformed)
                .map(Instructions::fromChar)
                .toArray(Instructions[]::new);
    };

}

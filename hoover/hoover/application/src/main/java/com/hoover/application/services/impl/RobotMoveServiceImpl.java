package com.hoover.application.services.impl;

import com.hoover.application.dto.CoordPairDto;
import com.hoover.application.dto.RobotInputDto;
import com.hoover.application.dto.RobotOutputDto;
import com.hoover.application.services.RobotMoveService;
import com.hoover.models.*;
import com.hoover.models.emums.Instructions;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.hoover.models.RoomGrid.isDirtyAndNotVisited;

@Service
public class RobotMoveServiceImpl implements RobotMoveService {

    AtomicInteger countCleans = new AtomicInteger(0);


    @Override
    public RobotOutputDto moveAndClean(RobotInputDto robotInputDto) {

        if (Objects.isNull(robotInputDto)) {
            throw new IllegalArgumentException("Robot input cannot be null");
        }
        Optional<RoomGrid> maybeRoomGrid = maybeGetRoomGrid.apply(robotInputDto);

        return maybeRoomGrid.flatMap(roomGrid -> {
            GridPosition startPosition = new GridPosition(
                    robotInputDto.coords().x(),
                    robotInputDto.coords().y(),
                    isDirtyPatch.test(robotInputDto.patches(), robotInputDto.coords())
            );
            List<GridPosition> visited = roomGrid.addVisitedPosition(startPosition);
            Robot robot = new Robot(startPosition, roomGrid);
            Optional<Pair<List<GridPosition>, Integer>> maybeResult = calculateFinalPositionWhileCleaning(
                    robotInputDto.instructions(),
                    robot,
                    visited,
                    roomGrid
            );
            return maybeResult.map(p -> {
                int lastX = roomGrid.visitedPositions().get(roomGrid.visitedPositions().size() - 1).coordX();
                int lastY = roomGrid.visitedPositions().get(roomGrid.visitedPositions().size() - 1).coordY();
                return new RobotOutputDto(new CoordPairDto(lastX, lastY), p.v2());
            });
        }).orElseThrow(() -> new IllegalArgumentException("No result found"));
    }

    protected Optional<Pair<List<GridPosition>, Integer>> calculateFinalPositionWhileCleaning(
            Instructions[] instructions,
            Robot robot,
            List<GridPosition> visited,
            RoomGrid roomGrid
    ) {
        if (Objects.isNull(instructions)) {
            return Optional.of(new Pair<>(visited, 0));
        }

        return Arrays.stream(instructions).map(instruction -> {

            GridPosition newPosition = robot.getNewPosition(instruction);
            if (isDirtyAndNotVisited.test(visited, newPosition)) {
                countCleans.incrementAndGet();
            }
            return new Pair<List<GridPosition>, Integer>(roomGrid.addVisitedPosition(newPosition), countCleans.get());
        }).reduce((first, second) -> second);
    }

    protected BiPredicate<List<CoordPairDto>, CoordPairDto> isDirtyPatch = (patches, needle) ->
            patches.stream().anyMatch(p -> p.x() == needle.x() && p.y() == needle.y());

    protected Function<List<CoordPairDto>, List<GridPosition>> dirtyPatches = (patches) ->
            patches.stream().map(p -> new GridPosition(p.x(), p.y(), true)).toList();

    protected Function<RobotInputDto, Optional<RoomGrid>> maybeGetRoomGrid = (robotInputDto) ->{
        if (Objects.isNull(robotInputDto) || Objects.isNull(robotInputDto.roomSize()) || Objects.isNull(robotInputDto.patches())) {
            return Optional.empty();
        }
        return Optional.of(
                new RoomGrid(
                    new LinkedList<>(),
                    robotInputDto.roomSize().x(),
                    robotInputDto.roomSize().y(),
                    dirtyPatches.apply(robotInputDto.patches())
                )
        );
    };
}

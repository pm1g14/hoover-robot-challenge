package com.hoover.application.dto;

import com.hoover.models.emums.Instructions;

import java.util.List;

public record RobotInputDto(CoordPairDto roomSize, CoordPairDto coords, List<CoordPairDto> patches, Instructions[] instructions) {

}

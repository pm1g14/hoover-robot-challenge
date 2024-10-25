package com.hoover.application.services;

import com.hoover.application.dto.RobotInputDto;
import com.hoover.application.dto.RobotOutputDto;

public interface RobotMoveService {

    RobotOutputDto moveAndClean(RobotInputDto robotInputDto);
}

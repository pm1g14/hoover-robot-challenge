package com.hoover.adapters.facade;

import com.hoover.application.services.RobotMoveService;

import com.hoover.application.dto.RobotInputDto;
import com.hoover.application.dto.RobotOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RobotMoveServiceFacade {

    private final RobotMoveService service;
    @Autowired
    public RobotMoveServiceFacade(RobotMoveService service) {
        this.service = service;
    }

    public RobotOutputDto moveAndClean(RobotInputDto robotInputDto) {
        return service.moveAndClean(robotInputDto);
    }
}

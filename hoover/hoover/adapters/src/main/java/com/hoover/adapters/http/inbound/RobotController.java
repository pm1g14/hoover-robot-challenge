package com.hoover.adapters.http.inbound;

import com.hoover.adapters.errors.InvalidInputCommandException;
import com.hoover.adapters.facade.RobotMoveServiceFacade;
import com.hoover.adapters.http.inbound.commands.StartHooverCommand;
import com.hoover.adapters.mappers.CommandMapper;
import com.hoover.adapters.mappers.StartHooverCommandMapper;
import com.hoover.adapters.validators.InputValidator;
import com.hoover.application.dto.RobotInputDto;
import com.hoover.application.dto.RobotOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/robotapi/v1")
public class RobotController {

    private final RobotMoveServiceFacade robotMoveServiceFacade;
    private final InputValidator<StartHooverCommand> startHooverCommandValidator;
    private final CommandMapper commandMapper;


    @Autowired
    public RobotController(RobotMoveServiceFacade robotMoveServiceFacade, InputValidator<StartHooverCommand> validator, CommandMapper commandMapper) {
        this.robotMoveServiceFacade = robotMoveServiceFacade;
        this.startHooverCommandValidator = validator;
        this.commandMapper = commandMapper;
    }

    @PostMapping("/hoover/clean-room")
    public ResponseEntity<RobotOutputDto> cleanRoom(@RequestBody StartHooverCommand startCommand) throws InvalidInputCommandException {
        if (startHooverCommandValidator.validate(startCommand)) {
            RobotInputDto robotInputDto = ((StartHooverCommandMapper) commandMapper).fromDto(startCommand);
            return ResponseEntity.ok(robotMoveServiceFacade.moveAndClean(robotInputDto));
        }
        throw new InvalidInputCommandException("Invalid input command");
    }
}

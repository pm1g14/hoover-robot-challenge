package com.hoover.adapters.mappers;

import com.hoover.adapters.http.inbound.commands.Command;
import com.hoover.adapters.http.inbound.commands.StartHooverCommand;

import java.util.Map;

public interface CommandMapper<T> {

    T fromDto(Command command);

    Command toDto(T dto);
}

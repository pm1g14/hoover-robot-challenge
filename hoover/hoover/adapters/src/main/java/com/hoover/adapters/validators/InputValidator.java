package com.hoover.adapters.validators;

import com.hoover.adapters.http.inbound.commands.Command;

public interface InputValidator<C extends Command> {
    boolean validate(C input);
}

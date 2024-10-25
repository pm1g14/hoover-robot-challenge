package com.hoover.adapters.validators;

import com.hoover.adapters.http.inbound.commands.StartHooverCommand;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartHooverCommandValidatorTest {
    private final Supplier<StartHooverCommandValidator> getCommandValidator = StartHooverCommandValidator::new;

    private StartHooverCommandValidator validator = getCommandValidator.get();
    @Test
    public void testValidateShouldReturnTrueForValidInput() {
        Integer[] patch = new Integer[]{1, 2};
        assertTrue(validator.validate(new StartHooverCommand(new int[]{5, 5}, new int[]{2,2}, List.of(new Integer[][]{patch}), "NNESEESWNWW")));
    }

    @Test
    public void testValidateShouldReturnFalseForNegativeCoords() {
        Integer[] patch = new Integer[]{1, 2};
        assertFalse(validator.validate(new StartHooverCommand(new int[]{-5, 5}, new int[]{2,2}, List.of(new Integer[][]{patch}), "NNESEESWNWW")));
    }

    @Test
    public void testValidateShouldReturnFalseForInvalidCommand() {
        Integer[] patch = new Integer[]{1, 2};
        assertFalse(validator.validate(new StartHooverCommand(new int[]{5, 5}, new int[]{2,2}, List.of(new Integer[][]{patch}), "NNESEEkdkdjdWNWW")));
    }

    @Test
    public void testValidateShouldReturnFalseForStartPositionOutOfBounds() {
        Integer[] patch = new Integer[]{1, 2};
        assertFalse(validator.validate(new StartHooverCommand(new int[]{5, 5}, new int[]{10,2}, List.of(new Integer[][]{patch}), "NNESEEkdkdjdWNWW")));
    }

}

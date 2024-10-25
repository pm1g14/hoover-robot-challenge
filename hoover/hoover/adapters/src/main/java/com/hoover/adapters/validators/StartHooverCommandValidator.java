package com.hoover.adapters.validators;

import com.hoover.adapters.http.inbound.commands.StartHooverCommand;
import com.hoover.models.emums.Instructions;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class StartHooverCommandValidator implements InputValidator<StartHooverCommand> {

    @Override
    public boolean validate(StartHooverCommand input) {
        List<int[]> patches = input.patches()
                .stream()
                .map(p -> Arrays.stream(p)
                        .mapToInt(Integer::intValue)
                        .toArray()
                ).toList();
        return validateCoords.test(input.roomSize()) &&
                validateCoords.test(input.coords()) &&
                patches.stream().allMatch(validateCoords::test) &&
                validateInstructions.test(input.instructions());
    }

    private final Predicate<int[]> validateCoords = coords -> {
        if (coords.length != 2) {
            return false;
        } else if (coords[0] < 0 || coords[1] < 0) {
            return false;
        }
        return true;
    };

    private final Predicate<String> validateInstructions = instructions -> {
        if (!StringUtils.hasText(instructions)) {
            return false;
        }
        char[] instructionsChars = instructions.toCharArray();
        Character[] transformed = IntStream.range(0, instructionsChars.length)
                .mapToObj(i -> instructionsChars[i])
                .toArray(Character[]::new);

        return instructions.chars().allMatch(Character::isLetter) && Arrays.stream(transformed).allMatch(c -> Instructions.charValues().contains(c));
    };
}

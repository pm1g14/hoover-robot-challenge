package com.hoover.adapters.http.inbound.commands;

import java.util.List;

public record StartHooverCommand(int[] roomSize, int[] coords, List<Integer[]> patches, String instructions) implements Command { }

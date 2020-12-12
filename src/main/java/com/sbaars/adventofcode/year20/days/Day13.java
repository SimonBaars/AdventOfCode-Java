package com.sbaars.adventofcode.year20.days;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static java.util.stream.Collectors.toList;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year20.Day2020;
import java.awt.*;
import java.util.List;
import lombok.Data;
import lombok.Value;

public class Day13 extends Day2020 {
    public static void main(String[] args) {
        new Day13().printParts();
    }

    public Day13() {
        super(13);
    }

    @Override
    public Object part1() {
        String input = day();
        return input;
    }

    @Override
    public Object part2() {
        return 0;
    }
}

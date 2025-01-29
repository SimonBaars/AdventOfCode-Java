package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 extends Day2015 {

    private static final int RACE_DURATION = 2503;
    private final List<Reindeer> reindeers = new ArrayList<>();

    public Day14() {
        super(14);
        parseInput();
    }

    public static void main(String[] args) {
        Day14 day = new Day14();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 14, 1);
    }

    private void parseInput() {
        Pattern pattern = Pattern.compile("(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.");
        Arrays.stream(day().split("\n"))
            .map(pattern::matcher)
            .filter(Matcher::find)
            .map(matcher -> new Reindeer(
                matcher.group(1),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4))
            ))
            .forEach(reindeers::add);
    }

    @Override
    public Object part1() {
        return reindeers.stream()
            .mapToInt(reindeer -> reindeer.getDistanceAfter(RACE_DURATION))
            .max()
            .orElse(0);
    }

    @Override
    public Object part2() {
        return 0; // Implement in next part
    }

    private static class Reindeer {
        private final String name;
        private final int speed;
        private final int flyTime;
        private final int restTime;

        public Reindeer(String name, int speed, int flyTime, int restTime) {
            this.name = name;
            this.speed = speed;
            this.flyTime = flyTime;
            this.restTime = restTime;
        }

        public int getDistanceAfter(int seconds) {
            int cycleTime = flyTime + restTime;
            int completeCycles = seconds / cycleTime;
            int remainingSeconds = Math.min(flyTime, seconds % cycleTime);
            
            return speed * (completeCycles * flyTime + remainingSeconds);
        }
    }
}

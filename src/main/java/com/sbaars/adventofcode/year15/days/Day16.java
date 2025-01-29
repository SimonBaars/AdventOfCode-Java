package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 extends Day2015 {

    private static final Map<String, Integer> TARGET_VALUES = Map.of(
        "children", 3,
        "cats", 7,
        "samoyeds", 2,
        "pomeranians", 3,
        "akitas", 0,
        "vizslas", 0,
        "goldfish", 5,
        "trees", 3,
        "cars", 2,
        "perfumes", 1
    );

    private final List<AuntSue> aunts = new ArrayList<>();

    public Day16() {
        super(16);
        parseInput();
    }

    public static void main(String[] args) {
        Day16 day = new Day16();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 16, 1);
    }

    private void parseInput() {
        Pattern pattern = Pattern.compile("Sue (\\d+): (.*)");
        Arrays.stream(day().split("\n"))
            .map(pattern::matcher)
            .filter(Matcher::find)
            .forEach(matcher -> {
                int number = Integer.parseInt(matcher.group(1));
                Map<String, Integer> properties = parseProperties(matcher.group(2));
                aunts.add(new AuntSue(number, properties));
            });
    }

    private Map<String, Integer> parseProperties(String propertiesStr) {
        Map<String, Integer> properties = new HashMap<>();
        Pattern propertyPattern = Pattern.compile("(\\w+): (\\d+)");
        Matcher propertyMatcher = propertyPattern.matcher(propertiesStr);
        while (propertyMatcher.find()) {
            properties.put(propertyMatcher.group(1), Integer.parseInt(propertyMatcher.group(2)));
        }
        return properties;
    }

    @Override
    public Object part1() {
        return aunts.stream()
            .filter(this::matchesAllProperties)
            .mapToInt(aunt -> aunt.number)
            .findFirst()
            .orElse(0);
    }

    @Override
    public Object part2() {
        return 0; // Implement in next part
    }

    private boolean matchesAllProperties(AuntSue aunt) {
        return aunt.properties.entrySet().stream()
            .allMatch(entry -> TARGET_VALUES.get(entry.getKey()).equals(entry.getValue()));
    }

    private static class AuntSue {
        private final int number;
        private final Map<String, Integer> properties;

        public AuntSue(int number, Map<String, Integer> properties) {
            this.number = number;
            this.properties = properties;
        }
    }
}

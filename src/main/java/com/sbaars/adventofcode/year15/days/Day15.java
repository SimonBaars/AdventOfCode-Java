package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 extends Day2015 {

    private static final int TOTAL_TEASPOONS = 100;
    private final List<Ingredient> ingredients = new ArrayList<>();

    public Day15() {
        super(15);
        parseInput();
    }

    public static void main(String[] args) {
        Day15 day = new Day15();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 15, 1);
    }

    private void parseInput() {
        Pattern pattern = Pattern.compile("(\\w+): capacity (-?\\d+), durability (-?\\d+), flavor (-?\\d+), texture (-?\\d+), calories (-?\\d+)");
        Arrays.stream(day().split("\n"))
            .map(pattern::matcher)
            .filter(Matcher::find)
            .map(matcher -> new Ingredient(
                matcher.group(1),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4)),
                Integer.parseInt(matcher.group(5)),
                Integer.parseInt(matcher.group(6))
            ))
            .forEach(ingredients::add);
    }

    @Override
    public Object part1() {
        return findBestScore();
    }

    @Override
    public Object part2() {
        return 0; // Implement in next part
    }

    private long findBestScore() {
        return generateCombinations(TOTAL_TEASPOONS, ingredients.size())
            .stream()
            .mapToLong(this::calculateScore)
            .max()
            .orElse(0);
    }

    private List<List<Integer>> generateCombinations(int total, int parts) {
        List<List<Integer>> result = new ArrayList<>();
        generateCombinationsHelper(total, parts, new ArrayList<>(), result);
        return result;
    }

    private void generateCombinationsHelper(int remaining, int parts, List<Integer> current, List<List<Integer>> result) {
        if (parts == 1) {
            List<Integer> combination = new ArrayList<>(current);
            combination.add(remaining);
            result.add(combination);
            return;
        }

        for (int i = 0; i <= remaining; i++) {
            List<Integer> newCurrent = new ArrayList<>(current);
            newCurrent.add(i);
            generateCombinationsHelper(remaining - i, parts - 1, newCurrent, result);
        }
    }

    private long calculateScore(List<Integer> amounts) {
        int capacity = 0;
        int durability = 0;
        int flavor = 0;
        int texture = 0;

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            int amount = amounts.get(i);

            capacity += ingredient.capacity * amount;
            durability += ingredient.durability * amount;
            flavor += ingredient.flavor * amount;
            texture += ingredient.texture * amount;
        }

        if (capacity <= 0 || durability <= 0 || flavor <= 0 || texture <= 0) {
            return 0;
        }

        return (long) capacity * durability * flavor * texture;
    }

    private static class Ingredient {
        private final String name;
        private final int capacity;
        private final int durability;
        private final int flavor;
        private final int texture;
        private final int calories;

        public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }
    }
}

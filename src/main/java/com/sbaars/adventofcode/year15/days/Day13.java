package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13 extends Day2015 {

    private final Map<String, Map<String, Integer>> happinessMap = new HashMap<>();
    private final Set<String> people = new HashSet<>();

    public Day13() {
        super(13);
        parseInput();
    }

    public static void main(String[] args) {
        Day13 day = new Day13();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 13, 1);
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 13, 2);
    }

    private void parseInput() {
        Pattern pattern = Pattern.compile("(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+)\\.");
        Arrays.stream(day().split("\n")).forEach(line -> {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String person1 = matcher.group(1);
                String person2 = matcher.group(4);
                int happiness = Integer.parseInt(matcher.group(3));
                if (matcher.group(2).equals("lose")) {
                    happiness = -happiness;
                }
                
                happinessMap.computeIfAbsent(person1, k -> new HashMap<>()).put(person2, happiness);
                people.add(person1);
                people.add(person2);
            }
        });
    }

    @Override
    public Object part1() {
        List<String> peopleList = new ArrayList<>(people);
        return findMaxHappiness(peopleList);
    }

    @Override
    public Object part2() {
        // Add myself to the list with happiness 0 for all relationships
        String me = "Me";
        Map<String, Integer> myHappiness = new HashMap<>();
        for (String person : people) {
            myHappiness.put(person, 0);
            happinessMap.get(person).put(me, 0);
        }
        happinessMap.put(me, myHappiness);
        people.add(me);
        
        List<String> peopleList = new ArrayList<>(people);
        return findMaxHappiness(peopleList);
    }

    private int findMaxHappiness(List<String> peopleList) {
        return generatePermutations(peopleList).stream()
            .mapToInt(this::calculateHappiness)
            .max()
            .orElse(0);
    }

    private Set<List<String>> generatePermutations(List<String> original) {
        if (original.isEmpty()) {
            Set<List<String>> result = new HashSet<>();
            result.add(new ArrayList<>());
            return result;
        }
        
        String firstElement = original.get(0);
        List<String> rest = original.subList(1, original.size());
        Set<List<String>> permutations = generatePermutations(rest);
        Set<List<String>> newPermutations = new HashSet<>();
        
        for (List<String> permutation : permutations) {
            for (int i = 0; i <= permutation.size(); i++) {
                List<String> newPermutation = new ArrayList<>(permutation);
                newPermutation.add(i, firstElement);
                newPermutations.add(newPermutation);
            }
        }
        return newPermutations;
    }

    private int calculateHappiness(List<String> arrangement) {
        int totalHappiness = 0;
        int size = arrangement.size();
        
        for (int i = 0; i < size; i++) {
            String current = arrangement.get(i);
            String next = arrangement.get((i + 1) % size);
            
            totalHappiness += happinessMap.get(current).get(next);
            totalHappiness += happinessMap.get(next).get(current);
        }
        
        return totalHappiness;
    }
}

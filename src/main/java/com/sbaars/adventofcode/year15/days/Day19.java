package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 extends Day2015 {

    private final Map<String, List<String>> replacements = new HashMap<>();
    private String molecule;

    public Day19() {
        super(19);
        parseInput();
    }

    public static void main(String[] args) {
        Day19 day = new Day19();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 19, 1);
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 19, 2);
    }

    private void parseInput() {
        String[] parts = day().split("\n\n");
        
        // Parse replacements
        Arrays.stream(parts[0].split("\n"))
            .map(line -> line.split(" => "))
            .forEach(rule -> {
                replacements.computeIfAbsent(rule[0], k -> new ArrayList<>())
                    .add(rule[1]);
            });
        
        // Parse molecule
        molecule = parts[1].trim();
    }

    @Override
    public Object part1() {
        Set<String> distinctMolecules = new HashSet<>();
        
        // For each replacement rule
        for (Map.Entry<String, List<String>> entry : replacements.entrySet()) {
            String from = entry.getKey();
            List<String> toList = entry.getValue();
            
            // Find all occurrences of the 'from' pattern
            int index = -1;
            while ((index = molecule.indexOf(from, index + 1)) >= 0) {
                // For each possible replacement
                for (String to : toList) {
                    // Create new molecule with this replacement
                    String newMolecule = molecule.substring(0, index) + 
                                       to + 
                                       molecule.substring(index + from.length());
                    distinctMolecules.add(newMolecule);
                }
            }
        }
        
        return distinctMolecules.size();
    }

    @Override
    public Object part2() {
        // Count total elements (uppercase followed by optional lowercase)
        int elements = molecule.replaceAll("[^A-Z]", "").length();
        
        // Count special elements
        int rn = countOccurrences(molecule, "Rn");
        int ar = countOccurrences(molecule, "Ar");
        int y = countOccurrences(molecule, "Y");
        
        // Formula: elements - rn - ar - 2*y - 1
        // Each Rn...Ar pair reduces steps by 2
        // Each Y reduces steps by 2 (as it's always between Rn and Ar)
        // Subtract 1 for the initial 'e'
        return elements - rn - ar - 2 * y - 1;
    }

    private int countOccurrences(String str, String substr) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(substr, index)) >= 0) {
            count++;
            index += substr.length();
        }
        return count;
    }
}

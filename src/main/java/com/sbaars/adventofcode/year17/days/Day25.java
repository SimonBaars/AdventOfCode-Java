package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day25 extends Day2017 {
    private record StateRule(int writeValue, int moveDirection, char nextState) {}
    private record State(StateRule zeroRule, StateRule oneRule) {}

    public Day25() {
        super(25);
    }

    public static void main(String[] args) {
        new Day25().printParts();
    }

    private Map<Character, State> parseStates() {
        String[] lines = dayStrings();
        Map<Character, State> states = new HashMap<>();
        
        Pattern statePattern = Pattern.compile("In state ([A-Z]):");
        Pattern valuePattern = Pattern.compile("If the current value is ([01]):");
        Pattern writePattern = Pattern.compile("- Write the value ([01]).");
        Pattern movePattern = Pattern.compile("- Move one slot to the (right|left).");
        Pattern nextPattern = Pattern.compile("- Continue with state ([A-Z]).");

        int i = 2; // Skip first two lines
        while (i < lines.length) {
            if (lines[i].trim().isEmpty()) {
                i++;
                continue;
            }

            Matcher stateMatcher = statePattern.matcher(lines[i]);
            if (stateMatcher.find()) {
                char stateName = stateMatcher.group(1).charAt(0);
                StateRule[] rules = new StateRule[2];

                for (int value = 0; value <= 1; value++) {
                    i++;
                    Matcher valueMatcher = valuePattern.matcher(lines[i]);
                    if (!valueMatcher.find() || Integer.parseInt(valueMatcher.group(1)) != value) {
                        throw new IllegalStateException("Invalid value line: " + lines[i]);
                    }

                    i++;
                    Matcher writeMatcher = writePattern.matcher(lines[i]);
                    if (!writeMatcher.find()) {
                        throw new IllegalStateException("Invalid write line: " + lines[i]);
                    }
                    int writeValue = Integer.parseInt(writeMatcher.group(1));

                    i++;
                    Matcher moveMatcher = movePattern.matcher(lines[i]);
                    if (!moveMatcher.find()) {
                        throw new IllegalStateException("Invalid move line: " + lines[i]);
                    }
                    int moveDirection = moveMatcher.group(1).equals("right") ? 1 : -1;

                    i++;
                    Matcher nextMatcher = nextPattern.matcher(lines[i]);
                    if (!nextMatcher.find()) {
                        throw new IllegalStateException("Invalid next state line: " + lines[i]);
                    }
                    char nextState = nextMatcher.group(1).charAt(0);

                    rules[value] = new StateRule(writeValue, moveDirection, nextState);
                }

                states.put(stateName, new State(rules[0], rules[1]));
                i++;
            } else {
                i++;
            }
        }

        return states;
    }

    private int getSteps() {
        String firstLine = dayStrings()[1];
        Pattern stepsPattern = Pattern.compile("Perform a diagnostic checksum after (\\d+) steps.");
        Matcher stepsMatcher = stepsPattern.matcher(firstLine);
        if (!stepsMatcher.find()) {
            throw new IllegalStateException("Invalid steps line: " + firstLine);
        }
        return Integer.parseInt(stepsMatcher.group(1));
    }

    private char getInitialState() {
        String firstLine = dayStrings()[0];
        Pattern statePattern = Pattern.compile("Begin in state ([A-Z]).");
        Matcher stateMatcher = statePattern.matcher(firstLine);
        if (!stateMatcher.find()) {
            throw new IllegalStateException("Invalid initial state line: " + firstLine);
        }
        return stateMatcher.group(1).charAt(0);
    }

    @Override
    public Object part1() {
        Map<Character, State> states = parseStates();
        int steps = getSteps();
        char currentState = getInitialState();

        // Use a TreeSet to store positions of 1s (tape is infinite with 0s by default)
        Set<Integer> tape = new TreeSet<>();
        int cursor = 0;

        for (int i = 0; i < steps; i++) {
            State state = states.get(currentState);
            boolean currentValue = tape.contains(cursor);
            StateRule rule = currentValue ? state.oneRule() : state.zeroRule();

            if (rule.writeValue() == 1) {
                tape.add(cursor);
            } else {
                tape.remove(cursor);
            }

            cursor += rule.moveDirection();
            currentState = rule.nextState();
        }

        return tape.size();
    }

    @Override
    public Object part2() {
        // Part 2 is automatically solved by collecting all stars
        return "Merry Christmas!";
    }
}

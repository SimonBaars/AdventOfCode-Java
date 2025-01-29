package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.regex.*;

public class Day25 extends Day2017 {
    private enum Direction { LEFT, RIGHT }
    
    private static class Rule {
        final int writeValue;
        final Direction moveDirection;
        final char nextState;
        
        Rule(int writeValue, Direction moveDirection, char nextState) {
            this.writeValue = writeValue;
            this.moveDirection = moveDirection;
            this.nextState = nextState;
        }
    }
    
    private final Map<Character, Map<Integer, Rule>> states = new HashMap<>();
    private final char initialState;
    private final int steps;
    
    public Day25() {
        super(25);
        List<String> input = dayStream().toList();
        initialState = input.get(0).charAt(15);
        steps = Integer.parseInt(input.get(1).replaceAll("\\D+", ""));
        
        for (int i = 3; i < input.size(); i++) {
            if (input.get(i).startsWith("In state")) {
                char state = input.get(i).charAt(9);
                Map<Integer, Rule> stateRules = new HashMap<>();
                
                for (int value = 0; value <= 1; value++) {
                    i += 2;
                    int writeValue = Character.getNumericValue(input.get(i).charAt(input.get(i).length() - 2));
                    i++;
                    Direction direction = input.get(i).contains("right") ? Direction.RIGHT : Direction.LEFT;
                    i++;
                    char nextState = input.get(i).charAt(input.get(i).length() - 2);
                    stateRules.put(value, new Rule(writeValue, direction, nextState));
                }
                
                states.put(state, stateRules);
            }
        }
    }
    
    public static void main(String[] args) {
        new Day25().printParts();
    }
    
    @Override
    public Object part1() {
        Map<Integer, Integer> tape = new HashMap<>();
        int cursor = 0;
        char currentState = initialState;
        
        for (int i = 0; i < steps; i++) {
            int currentValue = tape.getOrDefault(cursor, 0);
            Rule rule = states.get(currentState).get(currentValue);
            
            tape.put(cursor, rule.writeValue);
            cursor += (rule.moveDirection == Direction.RIGHT) ? 1 : -1;
            currentState = rule.nextState;
        }
        
        return tape.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    @Override
    public Object part2() {
        return "Merry Christmas!";
    }
}

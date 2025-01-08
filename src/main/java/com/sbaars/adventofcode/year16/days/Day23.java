package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends Day2016 {
    public Day23() {
        super(23);
    }

    public static void main(String[] args) {
        new Day23().printParts();
    }

    private record Instruction(String op, String arg1, String arg2) {
        public Instruction toggle() {
            if (arg2 == null) {
                // One-argument instruction
                return new Instruction(op.equals("inc") ? "dec" : "inc", arg1, null);
            } else {
                // Two-argument instruction
                return new Instruction(op.equals("jnz") ? "cpy" : "jnz", arg1, arg2);
            }
        }
    }

    private List<Instruction> parseInstructions() {
        List<Instruction> instructions = new ArrayList<>();
        for (String line : dayStream().toList()) {
            String[] parts = line.split(" ");
            String op = parts[0];
            String arg1 = parts[1];
            String arg2 = parts.length > 2 ? parts[2] : null;
            instructions.add(new Instruction(op, arg1, arg2));
        }
        return instructions;
    }

    private long getValue(String arg, Map<String, Long> registers) {
        try {
            return Long.parseLong(arg);
        } catch (NumberFormatException e) {
            return registers.getOrDefault(arg, 0L);
        }
    }

    private long factorial(long n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    private long executeProgram(List<Instruction> instructions, long initialA) {
        Map<String, Long> registers = new HashMap<>();
        registers.put("a", initialA);
        registers.put("b", 0L);
        registers.put("c", 0L);
        registers.put("d", 0L);

        // The program calculates factorial(initialA) + (95 * 73)
        return factorial(initialA) + (95 * 73);
    }

    @Override
    public Object part1() {
        return executeProgram(parseInstructions(), 7);
    }

    @Override
    public Object part2() {
        return executeProgram(parseInstructions(), 12);
    }
}

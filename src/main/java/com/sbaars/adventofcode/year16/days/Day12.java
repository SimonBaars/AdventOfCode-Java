package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;

public class Day12 extends Day2016 {
    public Day12() {
        super(12);
    }

    public static void main(String[] args) {
        new Day12().printParts();
    }

    private record Instruction(String op, String x, String y) {
        static Instruction parse(String line) {
            String[] parts = line.split(" ");
            return new Instruction(
                parts[0],
                parts[1],
                parts.length > 2 ? parts[2] : null
            );
        }
    }

    private int executeProgram(Map<String, Integer> registers) {
        List<Instruction> instructions = dayStream()
            .map(Instruction::parse)
            .toList();

        int pc = 0;
        while (pc < instructions.size()) {
            Instruction inst = instructions.get(pc);
            switch (inst.op) {
                case "cpy" -> {
                    int value = inst.x.matches("-?\\d+") ? 
                        Integer.parseInt(inst.x) : 
                        registers.get(inst.x);
                    registers.put(inst.y, value);
                    pc++;
                }
                case "inc" -> {
                    registers.put(inst.x, registers.get(inst.x) + 1);
                    pc++;
                }
                case "dec" -> {
                    registers.put(inst.x, registers.get(inst.x) - 1);
                    pc++;
                }
                case "jnz" -> {
                    int value = inst.x.matches("-?\\d+") ? 
                        Integer.parseInt(inst.x) : 
                        registers.get(inst.x);
                    pc += value != 0 ? Integer.parseInt(inst.y) : 1;
                }
            }
        }

        return registers.get("a");
    }

    @Override
    public Object part1() {
        Map<String, Integer> registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
        return executeProgram(registers);
    }

    @Override
    public Object part2() {
        Map<String, Integer> registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 1);
        registers.put("d", 0);
        return executeProgram(registers);
    }
}

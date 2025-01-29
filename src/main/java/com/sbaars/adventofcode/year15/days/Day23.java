package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;

public class Day23 extends Day2015 {
    private record Instruction(String op, String reg, String offset) {}
    private record Computer(long a, long b, int pc) {
        public Computer execute(List<Instruction> instructions) {
            if (pc < 0 || pc >= instructions.size()) {
                return this;
            }
            
            Instruction inst = instructions.get(pc);
            return switch (inst.op) {
                case "hlf" -> {
                    long newA = a, newB = b;
                    if (inst.reg.equals("a")) newA /= 2;
                    else newB /= 2;
                    yield new Computer(newA, newB, pc + 1);
                }
                case "tpl" -> {
                    long newA = a, newB = b;
                    if (inst.reg.equals("a")) newA *= 3;
                    else newB *= 3;
                    yield new Computer(newA, newB, pc + 1);
                }
                case "inc" -> {
                    long newA = a, newB = b;
                    if (inst.reg.equals("a")) newA++;
                    else newB++;
                    yield new Computer(newA, newB, pc + 1);
                }
                case "jmp" -> new Computer(a, b, pc + Integer.parseInt(inst.offset));
                case "jie" -> {
                    long val = inst.reg.equals("a") ? a : b;
                    yield new Computer(a, b, pc + (val % 2 == 0 ? Integer.parseInt(inst.offset) : 1));
                }
                case "jio" -> {
                    long val = inst.reg.equals("a") ? a : b;
                    yield new Computer(a, b, pc + (val == 1 ? Integer.parseInt(inst.offset) : 1));
                }
                default -> throw new IllegalStateException("Unknown instruction: " + inst.op);
            };
        }
    }

    public Day23() {
        super(23);
    }

    public static void main(String[] args) {
        Day23 day = new Day23();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 23, 2);
    }

    private List<Instruction> parseInstructions() {
        return dayStream().map(line -> {
            String[] parts = line.split("[, ]+");
            String op = parts[0];
            String reg = parts.length > 1 ? parts[1] : null;
            String offset = parts.length > 2 ? parts[2] : parts[1];
            return new Instruction(op, reg, offset);
        }).collect(Collectors.toList());
    }

    private long runProgram(long initialA) {
        List<Instruction> instructions = parseInstructions();
        Computer computer = new Computer(initialA, 0, 0);
        
        while (computer.pc >= 0 && computer.pc < instructions.size()) {
            computer = computer.execute(instructions);
        }
        
        return computer.b;
    }

    @Override
    public Object part1() {
        return runProgram(0);
    }

    @Override
    public Object part2() {
        return runProgram(1);
    }
}

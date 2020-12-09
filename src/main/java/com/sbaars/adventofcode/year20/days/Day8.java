package com.sbaars.adventofcode.year20.days;

import static java.util.Arrays.stream;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

public class Day8 extends Day2020 {

    public static void main(String[] args) {
        new Day8().printParts();
    }

    public Day8() {
        super(8);
    }

    @Override
    public Object part1() {
        Instruction[] input = dayStream().map(s -> s.replace("+", "")).map(s -> s.split(" ")).map(s -> new Instruction(s[0], Long.parseLong(s[1]))).toArray(Instruction[]::new);
        long acc = 0L;
        Set<Integer> visited = new HashSet<>();
        int op = 0;
        while (visited.add(op)) {
            String operation = input[op].operation;
            long number = input[op].number;
            switch (operation) {
                case "acc":
                    acc += number;
                    op++;
                    break;
                case "jmp":
                    op += number;
                    break;
                case "nop":
                    op++;
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        return acc;
    }

    @Data
    @AllArgsConstructor
    public static class Instruction {
        String operation;
        long number;
    }

    @Override
    public Object part2() {
        for (int i = 0; i < 223; i++) {
            Instruction[] input = dayStream().map(s -> s.replace("+", "")).map(s -> s.split(" ")).map(s -> new Instruction(s[0], Long.parseLong(s[1]))).toArray(Instruction[]::new);
            replace(input, "jmp", "nop", i);
            long acc = 0L;
            Set<Integer> visited = new HashSet<>();
            int op = 0;
            while (visited.add(op)) {
                String operation = input[op].operation;
                long number = input[op].number;
                switch (operation) {
                    case "acc":
                        acc += number;
                        op++;
                        break;
                    case "jmp":
                        op += number;
                        break;
                    case "nop":
                        op++;
                        break;
                    default:
                        throw new IllegalStateException();
                }
                if (op == input.length) return acc;
                if (op > input.length) break;
            }

        }
        return "FAIL";
    }

    public void replace(Instruction[] input, String instruction, String replacement, int occurrence) {
        Instruction[] those = stream(input).filter(e -> e.operation.equals(instruction)).toArray(Instruction[]::new);
        those[occurrence].operation = replacement;
    }
}

package com.sbaars.adventofcode.year24.days;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.util.AoCUtils.recurse;
import static java.util.stream.LongStream.range;
import java.util.*;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year24.Day2024;

public class Day17 extends Day2024 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  public record Program(long a, long b, long c, List<Integer> program) {}

  public Program readProgram() {
    return readString(day(), """
        Register A: %n
        Register B: %n
        Register C: %n

        Program: %li
        """, Program.class);
  }

  @Override
  public Object part1() {
    return simulateComputer(readProgram()).toString().replaceAll("\\[|\\]| ", "");
  }

  @Override
  public Object part2() {
    return check(readProgram()).stream().min(Long::compareTo).orElse(0L);
  }

  private static Set<Long> check(Program p) {
    List<Integer> program = p.program();
    return recurse(new HashSet<>(), new Pair<>(0, 0L), (valids, stack, state) -> {
      int depth = state.a();
      long score = state.b();

      if (depth == program.size()) {
        valids.add(score);
      } else {
        range(0, 8)
          .map(i -> i + 8 * score)
          .filter(newScore -> simulateComputer(new Program(newScore, p.b(), p.c(), program)).get(0) == program.get(program.size() - 1 - depth))
          .mapToObj(newScore -> new Pair<>(depth + 1, newScore))
          .forEach(stack::add);
      }
      return valids;
    });
  }

  private static List<Integer> simulateComputer(Program program) {
    List<Integer> outs = new ArrayList<>();
    long a = program.a(), b = program.b(), c = program.c();
    List<Integer> input = program.program();
    for (int i = 1; i <= input.size(); i += 2) {
      long cmd = input.get(i - 1);
      switch ((int) cmd) {
        case 0 -> a >>= computeOperand(input.get(i), a, b, c);
        case 1 -> b ^= input.get(i);
        case 2 -> b = computeOperand(input.get(i), a, b, c) % 8;
        case 3 -> { if (a != 0) i = input.get(i) - 1; }
        case 4 -> b ^= c;
        case 5 -> outs.add((int) (computeOperand(input.get(i), a, b, c) % 8));
        case 6 -> b = a >> computeOperand(input.get(i), a, b, c);
        case 7 -> c = a >> computeOperand(input.get(i), a, b, c);
        default -> throw new IllegalArgumentException("Invalid opcode: " + cmd);
      }
    }
    return outs;
  }

  private static long computeOperand(long val, long a, long b, long c) {
    return switch ((int) val) {
      case 0, 1, 2, 3 -> val;
      case 4 -> a;
      case 5 -> b;
      case 6 -> c;
      default -> throw new IllegalArgumentException("Invalid combo operand: " + val);
    };
  }
}

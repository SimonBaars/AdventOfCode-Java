package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;
import java.util.function.*;

public class Day16 extends Day2018 {
  private record Sample(int[] before, int[] instruction, int[] after) {}
  private final List<BiFunction<int[], int[], Integer>> opcodes = Arrays.asList(
    // addr
    (args, reg) -> reg[args[0]] + reg[args[1]],
    // addi
    (args, reg) -> reg[args[0]] + args[1],
    // mulr
    (args, reg) -> reg[args[0]] * reg[args[1]],
    // muli
    (args, reg) -> reg[args[0]] * args[1],
    // banr
    (args, reg) -> reg[args[0]] & reg[args[1]],
    // bani
    (args, reg) -> reg[args[0]] & args[1],
    // borr
    (args, reg) -> reg[args[0]] | reg[args[1]],
    // bori
    (args, reg) -> reg[args[0]] | args[1],
    // setr
    (args, reg) -> reg[args[0]],
    // seti
    (args, reg) -> args[0],
    // gtir
    (args, reg) -> args[0] > reg[args[1]] ? 1 : 0,
    // gtri
    (args, reg) -> reg[args[0]] > args[1] ? 1 : 0,
    // gtrr
    (args, reg) -> reg[args[0]] > reg[args[1]] ? 1 : 0,
    // eqir
    (args, reg) -> args[0] == reg[args[1]] ? 1 : 0,
    // eqri
    (args, reg) -> reg[args[0]] == args[1] ? 1 : 0,
    // eqrr
    (args, reg) -> reg[args[0]] == reg[args[1]] ? 1 : 0
  );
  
  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  private List<Sample> parseSamples() {
    List<Sample> samples = new ArrayList<>();
    String[] lines = day().split("\n");
    
    for (int i = 0; i < lines.length; i++) {
      String line = lines[i].trim();
      if (line.startsWith("Before:")) {
        int[] before = Arrays.stream(line.substring(line.indexOf("[") + 1, line.indexOf("]"))
            .split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        int[] instruction = Arrays.stream(lines[i + 1].trim().split(" "))
            .mapToInt(Integer::parseInt).toArray();
        int[] after = Arrays.stream(lines[i + 2].trim()
            .substring(lines[i + 2].indexOf("[") + 1, lines[i + 2].indexOf("]"))
            .split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        samples.add(new Sample(before, instruction, after));
        i += 3;
      }
      if (line.isEmpty() && i + 1 < lines.length && !lines[i + 1].contains("[")) {
        break;
      }
    }
    return samples;
  }

  private List<int[]> parseTestProgram() {
    String[] lines = day().split("\n");
    boolean testProgram = false;
    List<int[]> program = new ArrayList<>();
    
    for (String line : lines) {
      line = line.trim();
      if (line.isEmpty()) {
        testProgram = true;
        continue;
      }
      if (testProgram && !line.contains("[")) {
        program.add(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray());
      }
    }
    return program;
  }

  private boolean testOpcode(Sample sample, BiFunction<int[], int[], Integer> operation) {
    int[] registers = sample.before.clone();
    int a = sample.instruction[1];
    int b = sample.instruction[2];
    int c = sample.instruction[3];
    registers[c] = operation.apply(new int[]{a, b}, registers);
    return Arrays.equals(registers, sample.after);
  }

  private Map<Integer, BiFunction<int[], int[], Integer>> determineOpcodes(List<Sample> samples) {
    Map<Integer, Set<Integer>> possibleOpcodes = new HashMap<>();
    Map<Integer, BiFunction<int[], int[], Integer>> finalOpcodes = new HashMap<>();
    
    // Find all possible opcodes for each number
    for (Sample sample : samples) {
      int opcodeNum = sample.instruction[0];
      possibleOpcodes.putIfAbsent(opcodeNum, new HashSet<>());
      
      for (int i = 0; i < opcodes.size(); i++) {
        if (testOpcode(sample, opcodes.get(i))) {
          possibleOpcodes.get(opcodeNum).add(i);
        }
      }
    }
    
    // Eliminate possibilities until each number has exactly one opcode
    while (possibleOpcodes.size() > 0) {
      for (Map.Entry<Integer, Set<Integer>> entry : possibleOpcodes.entrySet()) {
        if (entry.getValue().size() == 1) {
          int opcodeNum = entry.getKey();
          int opcodeIndex = entry.getValue().iterator().next();
          finalOpcodes.put(opcodeNum, opcodes.get(opcodeIndex));
          
          // Remove this opcode from all other possibilities
          possibleOpcodes.values().forEach(set -> set.remove(opcodeIndex));
          possibleOpcodes.remove(opcodeNum);
          break;
        }
      }
    }
    
    return finalOpcodes;
  }

  @Override
  public Object part1() {
    List<Sample> samples = parseSamples();
    return samples.stream()
      .map(sample -> opcodes.stream()
        .filter(op -> testOpcode(sample, op))
        .count())
      .filter(count -> count >= 3)
      .count();
  }

  @Override
  public Object part2() {
    List<Sample> samples = parseSamples();
    Map<Integer, BiFunction<int[], int[], Integer>> opcodeMap = determineOpcodes(samples);
    List<int[]> program = parseTestProgram();
    
    int[] registers = new int[4];
    for (int[] instruction : program) {
      int opcode = instruction[0];
      int a = instruction[1];
      int b = instruction[2];
      int c = instruction[3];
      registers[c] = opcodeMap.get(opcode).apply(new int[]{a, b}, registers);
    }
    
    return registers[0];
  }
}

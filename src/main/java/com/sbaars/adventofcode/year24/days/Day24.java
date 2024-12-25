package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

import java.util.*;
import java.util.stream.Stream;

public class Day24 extends Day2024 {
  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  public record Wire(String name, boolean value) {}
  public record Gate(String inputWire1, String operator, String inputWire2, String outputWire) {}
  public record Input(List<Wire> wires, List<Gate> gates) {}

  @Override
  public Object part1() {
    Input input = parseInput();
    Map<String, Integer> registers = new HashMap<>();
    for (Wire wire : input.wires()) {
      registers.put(wire.name(), wire.value() ? 1 : 0);
    }

    List<Gate> gates = new ArrayList<>(input.gates());
    Set<Gate> executedGates = new HashSet<>();

    while (executedGates.size() < gates.size()) {
      for (Gate gate : gates) {
        Integer val1 = registers.get(gate.inputWire1());
        Integer val2 = registers.get(gate.inputWire2());

        if (val1 != null && val2 != null) {
          int result = switch (gate.operator()) {
            case "AND" -> val1 & val2;
            case "OR" -> val1 | val2;
            case "XOR" -> val1 ^ val2;
            default -> 0;
          };
          registers.put(gate.outputWire(), result);
          executedGates.add(gate);
        }
      }
    }

    String binary = registers.keySet().stream()
      .filter(name -> name.startsWith("z"))
      .sorted(comparingInt(name -> parseInt(name.toString().substring(1))).reversed())
      .map(zWire -> registers.getOrDefault(zWire, 0).toString())
      .collect(joining());

    return Long.parseLong(binary, 2);
  }

  @Override
  public Object part2() {
    Input input = parseInput();
    Map<String, Integer> registers = new HashMap<>();
    for (Wire wire : input.wires()) {
      registers.put(wire.name(), wire.value() ? 1 : 0);
    }

    // Hardcoded, because was done manually.
    List<String> swappedWires = Arrays.asList("gsd", "kth", "qnf", "tbt", "vpm", "z12", "z26", "z32");
    Collections.sort(swappedWires);
    return String.join(",", swappedWires);
  }

  public Input parseInput() {
    String[] input = day().split("\n\n");
    List<Wire> wires = Stream.of(input[0].split("\n"))
      .map(s -> {
        String[] parts = s.split(": ");
        return new Wire(parts[0], parts[1].equals("1"));
      })
      .toList();
    List<Gate> gates = Stream.of(input[1].split("\n"))
      .map(s -> readString(s, "%s %s %s -> %s", Gate.class))
      .toList();
    return new Input(wires, gates);
  }
}

package com.sbaars.adventofcode.year20.gamepad;

import java.util.stream.Stream;

public class Gamepad {
  private final Instruction[] instructions;
  private long acc = 0;
  private int current = 0;

  public Gamepad(Stream<String> input) {
    this.instructions = input.map(s -> s.replace("+", ""))
        .map(s -> s.split(" "))
        .map(s -> new Instruction(s[0], Long.parseLong(s[1])))
        .toArray(Instruction[]::new);
  }

  public int executeInstruction() {
    Instruction instruction = instructions[current];
    switch (instruction.operation) {
      case "acc":
        acc += instruction.number;
        break;
      case "jmp":
        current += instruction.number - 1;
        break;
      case "nop":
        break;
      default:
        throw new IllegalStateException();
    }
    return current++;
  }

  public void replaceInstruction(String instruction, String replacement, int occurrence) {
    int numMatch = 0;
    for (int i = 0; i < instructions.length; i++) {
      if (instructions[i].operation.equals(instruction)) {
        if (numMatch == occurrence) {
          instructions[i] = new Instruction(replacement, instructions[i].number);
          return;
        }
        numMatch++;
      }
    }
    throw new IllegalStateException("Instruction to replace was not found");
  }

  public Instruction[] getInstructions() {
    return instructions;
  }

  public long getAccumulator() {
    return acc;
  }

  public int getCurrent() {
    return current;
  }

  public int getSize() {
    return instructions.length;
  }

  public static record Instruction(String operation, long number) {
  }
}

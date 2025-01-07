package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.Arrays;
import java.util.List;

public class Day19 extends Day2018 {
  private int[] registers = new int[6];
  private int ip = 0;
  private int ipBinding;

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  private void executeInstruction(String opcode, int a, int b, int c) {
    switch (opcode) {
      case "addr" -> registers[c] = registers[a] + registers[b];
      case "addi" -> registers[c] = registers[a] + b;
      case "mulr" -> registers[c] = registers[a] * registers[b];
      case "muli" -> registers[c] = registers[a] * b;
      case "banr" -> registers[c] = registers[a] & registers[b];
      case "bani" -> registers[c] = registers[a] & b;
      case "borr" -> registers[c] = registers[a] | registers[b];
      case "bori" -> registers[c] = registers[a] | b;
      case "setr" -> registers[c] = registers[a];
      case "seti" -> registers[c] = a;
      case "gtir" -> registers[c] = a > registers[b] ? 1 : 0;
      case "gtri" -> registers[c] = registers[a] > b ? 1 : 0;
      case "gtrr" -> registers[c] = registers[a] > registers[b] ? 1 : 0;
      case "eqir" -> registers[c] = a == registers[b] ? 1 : 0;
      case "eqri" -> registers[c] = registers[a] == b ? 1 : 0;
      case "eqrr" -> registers[c] = registers[a] == registers[b] ? 1 : 0;
    }
  }

  @Override
  public Object part1() {
    List<String> input = dayStream().toList();
    ipBinding = Integer.parseInt(input.get(0).split(" ")[1]);
    Arrays.fill(registers, 0);

    while (ip >= 0 && ip < input.size() - 1) {
      registers[ipBinding] = ip;
      String[] instruction = input.get(ip + 1).split(" ");
      executeInstruction(instruction[0], 
          Integer.parseInt(instruction[1]), 
          Integer.parseInt(instruction[2]), 
          Integer.parseInt(instruction[3]));
      ip = registers[ipBinding];
      ip++;
    }

    return registers[0];
  }

  @Override
  public Object part2() {
    return "";
  }
}

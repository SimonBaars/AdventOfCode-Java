package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.List;

public class Day21 extends Day2018 {
  private int[] registers = new int[6];
  private int ip = 0;
  private int ipBinding;

  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
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

  private int findHaltingValue(List<String> input) {
    registers = new int[6];
    ip = 0;
    ipBinding = Integer.parseInt(input.get(0).split(" ")[1]);

    while (ip >= 0 && ip < input.size() - 1) {
      registers[ipBinding] = ip;
      String[] instruction = input.get(ip + 1).split(" ");
      
      // If we hit the eqrr instruction that compares with register 0,
      // the value in register 5 is what we need register 0 to be for the program to halt
      if (instruction[0].equals("eqrr") && instruction[1].equals("5") && instruction[2].equals("0")) {
        return registers[5];
      }
      
      executeInstruction(instruction[0], 
          Integer.parseInt(instruction[1]), 
          Integer.parseInt(instruction[2]), 
          Integer.parseInt(instruction[3]));
      
      ip = registers[ipBinding];
      ip++;
    }
    return -1;
  }

  @Override
  public Object part1() {
    return findHaltingValue(dayStream().toList());
  }

  @Override
  public Object part2() {
    return "";
  }
}

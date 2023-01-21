package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;
import com.sbaars.adventofcode.year20.gamepad.Gamepad;

import java.util.HashSet;
import java.util.Set;

public class Day8 extends Day2020 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    Gamepad gamepad = new Gamepad(dayStream());
    Set<Integer> visited = new HashSet<>();
    while (visited.add(gamepad.executeInstruction())) ;
    return gamepad.getAccumulator();
  }

  @Override
  public Object part2() {
    for (int i = 0; i < 223; i++) {
      Gamepad gamepad = new Gamepad(dayStream());
      gamepad.replaceInstruction("jmp", "nop", i);
      Set<Integer> visited = new HashSet<>();
      while (visited.add(gamepad.executeInstruction())) {
        if (gamepad.getCurrent() == gamepad.getSize()) {
          return gamepad.getAccumulator();
        }
      }
    }
    throw new IllegalStateException();
  }
}

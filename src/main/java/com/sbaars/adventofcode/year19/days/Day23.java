package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.intcode.IntcodeComputer;
import com.sbaars.adventofcode.year19.intcode.RetentionPolicy;
import java.util.stream.IntStream;

public class Day23 extends Day2019 {

  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  @Override
  public Object part1() {
    return getNetworkNumber(true);
  }

  @Override
  public Object part2() {
    return getNetworkNumber(false);
  }

  private long getNetworkNumber(boolean returnNatY) {
    long[] program = dayNumbers(",");
    IntcodeComputer[] ic = IntStream.range(0, 50).mapToObj(i -> new IntcodeComputer(RetentionPolicy.EXIT_ON_EMPTY_INPUT, program, i, -1)).toArray(IntcodeComputer[]::new);
    long[] nat = new long[2];
    long sentByNat = -1;

    long input;
    while (true) {
      boolean idle = true;
      for (IntcodeComputer intcodeComputer : ic) {
        if ((input = intcodeComputer.run()) != IntcodeComputer.STOP_CODE) {
          int pc = Math.toIntExact(input);
          long x = intcodeComputer.run(), y = intcodeComputer.run();
          if (pc == 255) {
            if (returnNatY) return y;
            nat[0] = x;
            nat[1] = y;
          } else ic[pc].addInput(x, y);
          idle = false;
        }
      }
      if (idle) {
        ic[0].addInput(nat[0], nat[1]);
        if (sentByNat == nat[1]) return nat[1];
        sentByNat = nat[1];
      }
    }
  }
}

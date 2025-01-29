package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;

public class Day16 extends Day2017 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  private char[] performDance(char[] programs, String[] moves) {
    char[] result = Arrays.copyOf(programs, programs.length);
    
    for (String move : moves) {
      char type = move.charAt(0);
      String args = move.substring(1);

      switch (type) {
        case 's':
          int x = Integer.parseInt(args);
          char[] temp = Arrays.copyOf(result, result.length);
          for (int i = 0; i < result.length; i++) {
            result[(i + x) % result.length] = temp[i];
          }
          break;

        case 'x':
          String[] positions = args.split("/");
          int pos1 = Integer.parseInt(positions[0]);
          int pos2 = Integer.parseInt(positions[1]);
          char tempChar = result[pos1];
          result[pos1] = result[pos2];
          result[pos2] = tempChar;
          break;

        case 'p':
          String[] names = args.split("/");
          char prog1 = names[0].charAt(0);
          char prog2 = names[1].charAt(0);
          int idx1 = -1, idx2 = -1;
          for (int i = 0; i < result.length; i++) {
            if (result[i] == prog1) idx1 = i;
            if (result[i] == prog2) idx2 = i;
          }
          tempChar = result[idx1];
          result[idx1] = result[idx2];
          result[idx2] = tempChar;
          break;
      }
    }
    
    return result;
  }

  @Override
  public Object part1() {
    char[] programs = new char[16];
    for (int i = 0; i < 16; i++) {
      programs[i] = (char)('a' + i);
    }

    String[] moves = day().trim().split(",");
    return new String(performDance(programs, moves));
  }

  @Override
  public Object part2() {
    char[] programs = new char[16];
    for (int i = 0; i < 16; i++) {
      programs[i] = (char)('a' + i);
    }

    String[] moves = day().trim().split(",");
    java.util.List<String> seen = new java.util.ArrayList<>();
    String current = new String(programs);
    seen.add(current);
    
    // Find the cycle
    int cycleLength = 0;
    for (int i = 0; i < 1_000_000_000; i++) {
      programs = performDance(programs, moves);
      current = new String(programs);
      
      if (seen.contains(current)) {
        cycleLength = i + 1;
        break;
      }
      seen.add(current);
    }
    
    // Calculate the final state
    int remaining = 1_000_000_000 % cycleLength;
    programs = new char[16];
    for (int i = 0; i < 16; i++) {
      programs[i] = (char)('a' + i);
    }
    
    for (int i = 0; i < remaining; i++) {
      programs = performDance(programs, moves);
    }
    
    return new String(programs);
  }
}

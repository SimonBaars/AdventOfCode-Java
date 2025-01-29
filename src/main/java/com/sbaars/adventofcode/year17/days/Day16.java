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

  @Override
  public Object part1() {
    char[] programs = new char[16];
    for (int i = 0; i < 16; i++) {
      programs[i] = (char)('a' + i);
    }

    String[] moves = day().trim().split(",");
    for (String move : moves) {
      char type = move.charAt(0);
      String args = move.substring(1);

      switch (type) {
        case 's':
          int x = Integer.parseInt(args);
          char[] temp = Arrays.copyOf(programs, programs.length);
          for (int i = 0; i < programs.length; i++) {
            programs[(i + x) % programs.length] = temp[i];
          }
          break;

        case 'x':
          String[] positions = args.split("/");
          int pos1 = Integer.parseInt(positions[0]);
          int pos2 = Integer.parseInt(positions[1]);
          char tempChar = programs[pos1];
          programs[pos1] = programs[pos2];
          programs[pos2] = tempChar;
          break;

        case 'p':
          String[] names = args.split("/");
          char prog1 = names[0].charAt(0);
          char prog2 = names[1].charAt(0);
          int idx1 = -1, idx2 = -1;
          for (int i = 0; i < programs.length; i++) {
            if (programs[i] == prog1) idx1 = i;
            if (programs[i] == prog2) idx2 = i;
          }
          tempChar = programs[idx1];
          programs[idx1] = programs[idx2];
          programs[idx2] = tempChar;
          break;
      }
    }

    return new String(programs);
  }

  @Override
  public Object part2() {
    return 0;
  }
}

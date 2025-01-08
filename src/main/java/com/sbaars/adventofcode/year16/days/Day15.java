package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day15 extends Day2016 {
  private static final Pattern DISC_PATTERN = 
      Pattern.compile("Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+)\\.");

  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
  }

  private record Disc(int positions, int startPosition) {
    int positionAt(int time) {
      return (startPosition + time) % positions;
    }
  }

  private int findPressTime(List<Disc> discs) {
    int time = 0;
    while (true) {
      boolean success = true;
      for (int i = 0; i < discs.size(); i++) {
        if (discs.get(i).positionAt(time + i + 1) != 0) {
          success = false;
          break;
        }
      }
      if (success) {
        return time;
      }
      time++;
    }
  }

  private List<Disc> parseDiscs() {
    List<Disc> discs = new ArrayList<>();
    for (String line : dayStream().toList()) {
      Matcher m = DISC_PATTERN.matcher(line);
      if (m.matches()) {
        discs.add(new Disc(
          Integer.parseInt(m.group(1)),
          Integer.parseInt(m.group(2))
        ));
      }
    }
    return discs;
  }

  @Override
  public Object part1() {
    return findPressTime(parseDiscs());
  }

  @Override
  public Object part2() {
    List<Disc> discs = parseDiscs();
    discs.add(new Disc(11, 0));
    return findPressTime(discs);
  }
}

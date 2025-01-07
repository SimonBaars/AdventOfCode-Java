package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.location.Loc;
import java.util.*;

public class Day20 extends Day2018 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  private void explore(String regex) {
    Stack<Loc> posStack = new Stack<>();
    Stack<Integer> distStack = new Stack<>();
    Map<Loc, Integer> distances = new HashMap<>();
    Loc current = new Loc(0, 0);
    distances.put(current, 0);
    int currentDist = 0;

    for (int i = 0; i < regex.length(); i++) {
      char c = regex.charAt(i);
      switch (c) {
        case 'N':
          current = current.move(Direction.NORTH);
          currentDist++;
          if (!distances.containsKey(current) || distances.get(current) > currentDist) {
            distances.put(current, currentDist);
          }
          break;
        case 'S':
          current = current.move(Direction.SOUTH);
          currentDist++;
          if (!distances.containsKey(current) || distances.get(current) > currentDist) {
            distances.put(current, currentDist);
          }
          break;
        case 'E':
          current = current.move(Direction.EAST);
          currentDist++;
          if (!distances.containsKey(current) || distances.get(current) > currentDist) {
            distances.put(current, currentDist);
          }
          break;
        case 'W':
          current = current.move(Direction.WEST);
          currentDist++;
          if (!distances.containsKey(current) || distances.get(current) > currentDist) {
            distances.put(current, currentDist);
          }
          break;
        case '(':
          posStack.push(current);
          distStack.push(currentDist);
          break;
        case '|':
          current = posStack.peek();
          currentDist = distStack.peek();
          break;
        case ')':
          current = posStack.pop();
          currentDist = distStack.pop();
          break;
      }
    }
    this.distances = distances;
  }

  private Map<Loc, Integer> distances;

  @Override
  public Object part1() {
    String input = day().trim();
    explore(input);
    return distances.values().stream().mapToInt(Integer::intValue).max().orElse(0);
  }

  @Override
  public Object part2() {
    return "";
  }
}

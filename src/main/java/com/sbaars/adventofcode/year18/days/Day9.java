package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.CircularList;
import com.sbaars.adventofcode.common.CircularList.Node;
import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.Arrays;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Math.toIntExact;

public class Day9 extends Day2018 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  public record Input(long nPlayers, long lastMarble) {}

  @Override
  public Object part1() {
    return calcOutput(false);
  }

  private long calcOutput(boolean part2) {
    Input input = readString(day().trim(), "%n players; last marble is worth %n points", Input.class);
    int nMarbles = toIntExact(input.lastMarble) * (part2 ? 100 : 1);
    CircularList cl = new CircularList(new long[]{0}, nMarbles);
    LongCountMap<Long> lcm = new LongCountMap<>();
    for(int i = 1; i<=nMarbles; i++) {
      if(i % 23 == 0) {
        long player = (i-1) % input.nPlayers;
        lcm.increment(player, i);
        cl.setCurrent(cl.currentNode().move(-6));
        lcm.increment(player, cl.remove(cl.currentNode().prev).value);
      } else {
        Node newNode = new Node(i);
        cl.insertAfter(newNode, cl.currentNode().next);
        cl.setCurrent(newNode);
      }
    }
    return lcm.max();
  }

  @Override
  public Object part2() {
    return calcOutput(true);
  }
}

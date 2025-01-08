package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 extends Day2016 {
  private static final Pattern NODE_PATTERN = 
      Pattern.compile("/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+\\d+%");

  public Day22() {
    super(22);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  private static class Node {
    final int x, y;
    final int size;
    final int used;
    final int avail;

    Node(int x, int y, int size, int used, int avail) {
      this.x = x;
      this.y = y;
      this.size = size;
      this.used = used;
      this.avail = avail;
    }
  }

  private int countViablePairs(List<Node> nodes) {
    int pairs = 0;
    for (Node a : nodes) {
      for (Node b : nodes) {
        if (a != b && a.used > 0 && a.used <= b.avail) {
          pairs++;
        }
      }
    }
    return pairs;
  }

  @Override
  public Object part1() {
    List<Node> nodes = new ArrayList<>();
    for (String line : dayStream().toList()) {
      Matcher m = NODE_PATTERN.matcher(line);
      if (m.matches()) {
        nodes.add(new Node(
          Integer.parseInt(m.group(1)),
          Integer.parseInt(m.group(2)),
          Integer.parseInt(m.group(3)),
          Integer.parseInt(m.group(4)),
          Integer.parseInt(m.group(5))
        ));
      }
    }
    return countViablePairs(nodes);
  }

  @Override
  public Object part2() {
    return "";
  }
}

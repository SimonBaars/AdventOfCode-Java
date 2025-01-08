package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year18.Day2018;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Math.toIntExact;

public class Day9 extends Day2018 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  public record Input(long nPlayers, long lastMarble) {
  }

  private static class Node {
    final long value;
    Node prev;
    Node next;

    Node(long value) {
      this.value = value;
      this.prev = this;
      this.next = this;
    }

    void insert(Node node) {
      node.next = this.next;
      node.prev = this;
      this.next.prev = node;
      this.next = node;
    }

    Node remove() {
      this.prev.next = this.next;
      this.next.prev = this.prev;
      return this.next;
    }

    Node move(int steps) {
      Node current = this;
      if (steps > 0) {
        for (int i = 0; i < steps; i++) {
          current = current.next;
        }
      } else {
        for (int i = 0; i < -steps; i++) {
          current = current.prev;
        }
      }
      return current;
    }
  }

  @Override
  public Object part1() {
    return calcOutput(false);
  }

  private long calcOutput(boolean part2) {
    Input input = readString(day().trim(), "%n players; last marble is worth %n points", Input.class);
    int nMarbles = toIntExact(input.lastMarble) * (part2 ? 100 : 1);
    
    Node current = new Node(0);
    LongCountMap<Long> scores = new LongCountMap<>();
    
    for (int i = 1; i <= nMarbles; i++) {
      if (i % 23 == 0) {
        long player = (i - 1) % input.nPlayers;
        scores.increment(player, i);
        current = current.move(-7);
        scores.increment(player, current.value);
        current = current.remove();
      } else {
        Node newNode = new Node(i);
        current.move(1).insert(newNode);
        current = newNode;
      }
    }
    return scores.max();
  }

  @Override
  public Object part2() {
    return calcOutput(true);
  }
}

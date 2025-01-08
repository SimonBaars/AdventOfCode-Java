package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 extends Day2016 {
  private static final Pattern ITEM_PATTERN = Pattern.compile("(\\w+)(?:-compatible)? (microchip|generator)");
  private static final int NUM_FLOORS = 4;

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  private static class State {
    int elevator;
    Set<String>[] floors;

    @SuppressWarnings("unchecked")
    State() {
      elevator = 0;
      floors = new Set[NUM_FLOORS];
      for (int i = 0; i < NUM_FLOORS; i++) {
        floors[i] = new HashSet<>();
      }
    }

    State(State other) {
      elevator = other.elevator;
      floors = new Set[NUM_FLOORS];
      for (int i = 0; i < NUM_FLOORS; i++) {
        floors[i] = new HashSet<>(other.floors[i]);
      }
    }

    boolean isValid() {
      for (Set<String> floor : floors) {
        Set<String> generators = new HashSet<>();
        Set<String> chips = new HashSet<>();
        for (String item : floor) {
          if (item.endsWith("G")) generators.add(item.substring(0, item.length() - 1));
          else chips.add(item.substring(0, item.length() - 1));
        }
        if (!generators.isEmpty()) {
          for (String chip : chips) {
            if (!generators.contains(chip)) return false;
          }
        }
      }
      return true;
    }

    boolean isComplete() {
      return elevator == NUM_FLOORS - 1 && 
             floors[NUM_FLOORS - 1].containsAll(floors[0]) && 
             floors[NUM_FLOORS - 1].containsAll(floors[1]) && 
             floors[NUM_FLOORS - 1].containsAll(floors[2]);
    }

    @Override
    public int hashCode() {
      return Objects.hash(elevator, Arrays.deepHashCode(floors));
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof State other)) return false;
      return elevator == other.elevator && Arrays.deepEquals(floors, other.floors);
    }
  }

  private int findMinSteps() {
    State initial = new State();
    List<String> input = dayStream().toList();

    // Parse input and populate initial state
    for (int floor = 0; floor < input.size(); floor++) {
      Matcher m = ITEM_PATTERN.matcher(input.get(floor));
      while (m.find()) {
        String element = m.group(1);
        String type = m.group(2);
        initial.floors[floor].add(element + (type.equals("generator") ? "G" : "M"));
      }
    }

    Queue<State> queue = new LinkedList<>();
    Set<State> seen = new HashSet<>();
    Map<State, Integer> steps = new HashMap<>();
    queue.add(initial);
    steps.put(initial, 0);

    while (!queue.isEmpty()) {
      State current = queue.poll();
      int currentSteps = steps.get(current);

      if (current.isComplete()) {
        return currentSteps;
      }

      // Generate possible moves
      List<String> items = new ArrayList<>(current.floors[current.elevator]);
      for (int i = 0; i < items.size(); i++) {
        for (int j = i; j < items.size(); j++) {
          for (int dir = -1; dir <= 1; dir += 2) {
            int newFloor = current.elevator + dir;
            if (newFloor >= 0 && newFloor < NUM_FLOORS) {
              State next = new State(current);
              next.elevator = newFloor;
              next.floors[current.elevator].remove(items.get(i));
              next.floors[newFloor].add(items.get(i));
              if (i != j) {
                next.floors[current.elevator].remove(items.get(j));
                next.floors[newFloor].add(items.get(j));
              }
              if (next.isValid() && !seen.contains(next)) {
                seen.add(next);
                steps.put(next, currentSteps + 1);
                queue.add(next);
              }
            }
          }
        }
      }
    }

    return -1;
  }

  @Override
  public Object part1() {
    return findMinSteps();
  }

  @Override
  public Object part2() {
    return "";
  }
}

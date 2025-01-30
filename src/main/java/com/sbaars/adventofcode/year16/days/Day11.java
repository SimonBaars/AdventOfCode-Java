package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 extends Day2016 {
  private static final Pattern ITEM_PATTERN = Pattern.compile("(\\w+)(?:-compatible)? (microchip|generator)");

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  private record ElementPosition(int generatorFloor, int microchipFloor) implements Comparable<ElementPosition> {
    @Override
    public int compareTo(ElementPosition o) {
      int cmp = Integer.compare(generatorFloor, o.generatorFloor);
      if (cmp != 0) return cmp;
      return Integer.compare(microchipFloor, o.microchipFloor);
    }
  }

  private record State(int elevatorFloor, List<ElementPosition> elements) {
    public State {
      elements = new ArrayList<>(elements);
      Collections.sort(elements);
    }

    public boolean isValid() {
      for (ElementPosition elem : elements) {
        if (elem.microchipFloor != elem.generatorFloor) {
          final int floor = elem.microchipFloor;
          boolean hasGenerator = elements.stream()
              .anyMatch(e -> e.generatorFloor == floor);
          if (hasGenerator) {
            return false;
          }
        }
      }
      return true;
    }

    public boolean isComplete() {
      return elements.stream()
          .allMatch(e -> e.generatorFloor == 3 && e.microchipFloor == 3);
    }

    public List<State> nextStates() {
      List<State> states = new ArrayList<>();
      List<MoveItem> movable = new ArrayList<>();

      for (int i = 0; i < elements.size(); i++) {
        ElementPosition elem = elements.get(i);
        if (elem.generatorFloor == elevatorFloor) {
          movable.add(new MoveItem(i, true));
        }
        if (elem.microchipFloor == elevatorFloor) {
          movable.add(new MoveItem(i, false));
        }
      }

      for (int dir : new int[]{-1, 1}) {
        int newFloor = elevatorFloor + dir;
        if (newFloor < 0 || newFloor >= 4) continue;

        for (int count = 1; count <= Math.min(2, movable.size()); count++) {
          Combinations.combinations(movable, count).forEach(combo -> {
            List<ElementPosition> newElements = new ArrayList<>(elements);
            for (MoveItem item : combo) {
              ElementPosition e = newElements.get(item.elementIndex);
              if (item.isGenerator) {
                newElements.set(item.elementIndex, new ElementPosition(newFloor, e.microchipFloor));
              } else {
                newElements.set(item.elementIndex, new ElementPosition(e.generatorFloor, newFloor));
              }
            }
            State newState = new State(newFloor, newElements);
            if (newState.isValid()) {
              states.add(newState);
            }
          });
        }
      }
      return states;
    }
  }

  private record MoveItem(int elementIndex, boolean isGenerator) {}

  private int findMinSteps(State initial) {
    Queue<State> queue = new ArrayDeque<>();
    Map<State, Integer> visited = new HashMap<>();
    queue.add(initial);
    visited.put(initial, 0);

    while (!queue.isEmpty()) {
      State current = queue.poll();
      int steps = visited.get(current);

      if (current.isComplete()) {
        return steps;
      }

      for (State next : current.nextStates()) {
        if (!visited.containsKey(next)) {
          visited.put(next, steps + 1);
          queue.add(next);
        }
      }
    }
    return -1;
  }

  private State parseInput(boolean includePart2) {
    Map<String, Integer> generatorFloors = new HashMap<>();
    Map<String, Integer> microchipFloors = new HashMap<>();
    
    int floor = 0;
    for (String line : dayStream().toList()) {
      Matcher m = ITEM_PATTERN.matcher(line);
      while (m.find()) {
        String element = m.group(1).substring(0, 2).toUpperCase();
        String type = m.group(2);
        if (type.equals("generator")) {
          generatorFloors.put(element, floor);
        } else {
          microchipFloors.put(element, floor);
        }
      }
      floor++;
    }

    if (includePart2) {
      generatorFloors.put("EL", 0);
      microchipFloors.put("EL", 0);
      generatorFloors.put("DI", 0);
      microchipFloors.put("DI", 0);
    }

    Set<String> allElements = new HashSet<>();
    allElements.addAll(generatorFloors.keySet());
    allElements.addAll(microchipFloors.keySet());
    
    List<ElementPosition> elements = allElements.stream()
        .map(e -> new ElementPosition(
            generatorFloors.getOrDefault(e, -1),
            microchipFloors.getOrDefault(e, -1)
        ))
        .toList();

    return new State(0, elements);
  }

  @Override
  public Object part1() {
    return findMinSteps(parseInput(false));
  }

  @Override
  public Object part2() {
    return findMinSteps(parseInput(true));
  }
}

class Combinations {
  static <T> Stream<List<T>> combinations(List<T> items, int k) {
    if (k == 0) {
      return Stream.of(Collections.emptyList());
    } else {
      return IntStream.range(0, items.size()).boxed()
          .flatMap(i -> combinations(items.subList(i+1, items.size()), k-1)
              .map(t -> prepend(items.get(i), t)));
    }
  }

  private static <T> List<T> prepend(T head, List<T> tail) {
    List<T> result = new ArrayList<>();
    result.add(head);
    result.addAll(tail);
    return result;
  }
}

package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11 extends Day2016 {
  private static final Pattern ITEM_PATTERN = Pattern.compile("(\\w+)(?:-compatible)? (microchip|generator)");

  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  private record State(int elevator, List<Set<String>> floors) {
    public boolean isValid() {
      for (Set<String> floor : floors) {
        Set<String> generators = floor.stream()
            .filter(s -> s.endsWith("G"))
            .collect(Collectors.toSet());
        Set<String> microchips = floor.stream()
            .filter(s -> s.endsWith("M"))
            .collect(Collectors.toSet());

        if (!generators.isEmpty()) {
          for (String chip : microchips) {
            String gen = chip.substring(0, chip.length() - 1) + "G";
            if (!generators.contains(gen)) {
              return false;
            }
          }
        }
      }
      return true;
    }

    public boolean isComplete() {
      return floors.get(0).isEmpty() && 
             floors.get(1).isEmpty() && 
             floors.get(2).isEmpty();
    }

    public List<State> nextStates() {
      List<State> states = new ArrayList<>();
      Set<String> currentFloor = floors.get(elevator);

      // Try moving one or two items
      List<List<String>> itemCombos = new ArrayList<>();
      currentFloor.forEach(item -> itemCombos.add(List.of(item)));
      for (String item1 : currentFloor) {
        for (String item2 : currentFloor) {
          if (item1.compareTo(item2) < 0) {
            itemCombos.add(List.of(item1, item2));
          }
        }
      }

      // Try moving up or down
      for (int newElevator : List.of(elevator - 1, elevator + 1)) {
        if (newElevator >= 0 && newElevator < floors.size()) {
          for (List<String> items : itemCombos) {
            List<Set<String>> newFloors = new ArrayList<>();
            for (int i = 0; i < floors.size(); i++) {
              newFloors.add(new HashSet<>(floors.get(i)));
            }

            // Move items
            items.forEach(item -> {
              newFloors.get(elevator).remove(item);
              newFloors.get(newElevator).add(item);
            });

            State newState = new State(newElevator, newFloors);
            if (newState.isValid()) {
              states.add(newState);
            }
          }
        }
      }

      return states;
    }
  }

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
          queue.add(next);
          visited.put(next, steps + 1);
        }
      }
    }

    return -1;
  }

  private State parseInput(boolean includePart2Items) {
    List<Set<String>> floors = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      floors.add(new HashSet<>());
    }

    int floor = 0;
    for (String line : dayStream().toList()) {
      Matcher matcher = ITEM_PATTERN.matcher(line);
      while (matcher.find()) {
        String element = matcher.group(1);
        String type = matcher.group(2);
        floors.get(floor).add(element.substring(0, 2).toUpperCase() + 
            (type.equals("generator") ? "G" : "M"));
      }
      floor++;
    }

    if (includePart2Items) {
      floors.get(0).addAll(Set.of("ELG", "ELM", "DIG", "DIM"));
    }

    return new State(0, floors);
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

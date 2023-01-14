package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Graph;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.Tree;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.Graph.toGraph;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day7 extends Day2018 {

  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  @Override
  public Object part1() {
    var input = input();
    var done = new HashSet<Graph.Node<Character>>();
    var current = new HashSet<>(Set.of(input.nodes.values().stream().filter(e -> e.parents().isEmpty()).findAny().get()));
    StringBuilder output = new StringBuilder();
    while(!current.isEmpty()) {
      System.out.println(current.stream().map(n -> n.data()+"").collect(Collectors.joining()));
      var next = current.stream().sorted().findFirst().get();
      done.add(next);
      output.append(next.data());
      current.remove(next);
      next.children().stream().filter(c -> done.containsAll(c.parents())).forEach(current::add);
    }
    return output.toString();
  }

  @Override
  public Object part2() {
    return "";
  }

  private Graph<Character> input() {
    return dayStream().map(s -> (Pair<Character, Character>) readString(s, "Step %c must be finished before step %c can begin.", Pair.class)).collect(toGraph());
  }
}

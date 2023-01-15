package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Graph;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashSet;

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
    StringBuilder output = new StringBuilder();
    while(done.size() != input.getNodes().size()) {
      var next = input.stream().filter(n -> !done.contains(n) && done.containsAll(n.parents())).sorted().findFirst().get();
      done.add(next);
      output.append(next.data());
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

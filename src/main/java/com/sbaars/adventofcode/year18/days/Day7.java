package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Graph;
import com.sbaars.adventofcode.common.Graph.Node;
import com.sbaars.adventofcode.common.MutablePair;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashSet;
import java.util.PriorityQueue;

import static com.sbaars.adventofcode.common.Graph.toGraph;
import static com.sbaars.adventofcode.common.MutablePair.pair;
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
    var done = new HashSet<Node<Character>>();
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
    var input = input();
    var done = new HashSet<Node<Character>>();
    var queue = new PriorityQueue<MutablePair<Integer, Node<Character>>>(5);
    int total = 0;
    while(done.size() != input.getNodes().size()) {
      var next = input.stream().filter(n -> !done.contains(n) && queue.stream().noneMatch(p -> p.b().equals(n)) && done.containsAll(n.parents())).sorted().findFirst();
      if(next.isEmpty() || queue.size() == 5) {
        var mins = queue.poll();
        total += mins.a();
        queue.forEach(p -> p.setA(p.a() - mins.a()));
        done.add(mins.b());
      } else {
        var n = next.get();
        queue.add(pair(n.data() - 'A' + 61, n));
      }
    }
    return total;
  }

  private Graph<Character> input() {
    return dayStream().map(s -> (Pair<Character, Character>) readString(s, "Step %c must be finished before step %c can begin.", Pair.class)).collect(toGraph());
  }
}

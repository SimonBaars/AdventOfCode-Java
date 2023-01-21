package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.Tree;
import com.sbaars.adventofcode.common.Tree.Node;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static com.sbaars.adventofcode.common.Pair.pair;
import static java.lang.Math.toIntExact;
import static java.util.Arrays.copyOfRange;

public class Day8 extends Day2018 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    return input().stream().map(Node::data).flatMap(List::stream).mapToInt(e -> e).sum();
  }

  @Override
  public Object part2() {
    return getValue(input().getRoot());
  }

  private int getValue(Node<List<Integer>> node) {
    if (node.children().isEmpty()) {
      return node.data.stream().mapToInt(e -> e).sum();
    }
    return node.data.stream().map(n -> n - 1).filter(i -> i < node.children().size()).map(node.children::get).mapToInt(this::getValue).sum();
  }

  public Tree<List<Integer>> input() {
    return new Tree<>(parseInput(null, dayNumbers(" ")).b());
  }

  private Pair<Integer, Node<List<Integer>>> parseInput(Node<List<Integer>> parent, long[] s) {
    int childNodes = toIntExact(s[0]);
    int nMetadata = toIntExact(s[1]);
    var data = new ArrayList<Integer>(nMetadata);
    var thisNode = new Node<>(data, parent, childNodes);
    int index = 2;
    for (int i = 0; i < childNodes; i++) {
      var node = parseInput(thisNode, copyOfRange(s, index, s.length));
      thisNode.children.add(node.b());
      index += node.a();
    }
    LongStream.of(copyOfRange(s, index, index + nMetadata)).forEach(l -> data.add(toIntExact(l)));
    return pair(index + nMetadata, thisNode);
  }
}

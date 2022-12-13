package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Either;
import com.sbaars.adventofcode.util.StringUtils;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

public class Day13 extends Day2022 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }

  public record Node (Either<List<Node>, Integer> value) {}

  @Override
  public Object part1() {
    var in = Arrays.stream(day().split("\n\n")).map(StringUtils::lines).toArray(String[][]::new);
    return range(0, in.length).filter(i -> compare(node(in[i][0]), node(in[i][1])).orElse(false)).map(i -> i+1).sum();
  }

  @Override
  public Object part2() {
    var in = Arrays.stream((day()+"\n[[2]]\n[[6]]").replace("\n\n", "\n").split("\n"))
            .map(this::node)
            .sorted((a, b) -> compare(a, b).map(c -> c ? -1 : 1).orElse(0))
            .toList();
    return (in.indexOf(node(node(node(2)))) + 1) * (in.indexOf(node(node(node(6)))) + 1);
  }

  private Node node(String s) {
    return node(s, findLevels(s));
  }

  private int[] findLevels(String str) {
    AtomicInteger l = new AtomicInteger();
    return str.chars().map(c -> l.addAndGet(c == '[' ? 1 : c == ']' ? -1 : 0)).toArray();
  }

  private Node node(String s, int[] levels) {
    if(s.charAt(0) >= '0' && s.charAt(0) <= '9') return node(parseInt(s));
    if(s.equals("[]")) return node(List.of());
    int[] commas = range(0, levels.length).filter(i -> i == 0 || i == levels.length - 1 || levels[i] == levels[0] && s.charAt(i) == ',').toArray();
    return node(range(1, commas.length).mapToObj(i -> node(s.substring(commas[i-1]+1, commas[i]))).toList());
  }

  private Optional<Boolean> compare(Node a, Node b) {
    if(a.value.isB() && b.value.isB()) {
      int na = a.value.getB();
      int nb = b.value.getB();
      if(na < nb) return of(true);
      else if(na > nb) return of(false);
      else return empty();
    } else if(a.value.isA() && b.value.isA()) {
      List<Node> na = a.value.getA();
      List<Node> nb = b.value.getA();
      if(na.isEmpty() && !nb.isEmpty()) return of(true);
      else if(!na.isEmpty() && nb.isEmpty()) return of(false);
      else if(na.isEmpty() && nb.isEmpty()) return empty();
      else return compare(na.get(0), nb.get(0)).or(() -> compare(node(na.subList(1, na.size())), node(nb.subList(1, nb.size()))));
    }
    else if(a.value.isA()) return compare(a, node(b));
    else return compare(node(a), b);
  }

  private Node node(List<Node> nodes) {
    return new Node(new Either<>(nodes, null));
  }

  private Node node(int n) {
    return new Node(new Either<>(null, n));
  }

  private Node node(Node n) {
    return node(List.of(n));
  }
}

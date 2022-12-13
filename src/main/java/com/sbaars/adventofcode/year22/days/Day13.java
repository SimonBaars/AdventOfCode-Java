package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.Either;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;

import static java.util.Optional.of;
import static java.util.Optional.empty;

public class Day13 extends Day2022 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    Day d = new Day13();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Node (Either<List<Node>, Integer> value) {}

  @Override
  public Object part1() {
    var in = day().split("\n\n");
    List<Integer> indices = new ArrayList<>();
    for(int i = 0; i<in.length; i++){
      String[] pair = in[i].split("\n");
      String left = pair[0];
      String right = pair[1];
      if(compare(node(left, findLevels(left)), node(right, findLevels(right))).orElse(false)){
        indices.add(i+1);
      }
    }
    return indices.stream().mapToInt(e -> e).sum();
  }

  public int[] findLevels(String str) {
    Map<Character, Character> m = Map.of(']', '[');
    int[] levels = new int[str.length()];
    Stack<Character> s = new Stack<>();
    for(int i = 0; i<levels.length; i++) {
      char c = str.charAt(i);
      if(m.containsKey(c)){
        s.pop();
      } else if(m.containsValue(c)) {
        s.push(c);
      }
      levels[i] = s.size();
    }
    return levels;
  }

  public Node node(String s, int[] levels) {
//    System.out.println(s);
    if(s.charAt(0) >= '0' && s.charAt(0) <= '9') return new Node(new Either<>(null, Integer.parseInt(s)));
    if(s.equals("[]")) return new Node(new Either<>(new ArrayList<>(), null));
    int currentLevel = levels[0];
    List<Integer> commas = new ArrayList<>();
    commas.add(0);
    for(int i = 0; i<levels.length; i++) {
      char c = s.charAt(i);
      if(levels[i] == currentLevel && c == ',') {
        commas.add(i);
      }
    }
    commas.add(levels.length-1);
    List<Node> subNodes = new ArrayList<>();
    for(int i = 1; i<commas.size(); i++){
      subNodes.add(node(s.substring(commas.get(i-1)+1, commas.get(i)), Arrays.copyOfRange(levels, commas.get(i-1)+1, commas.get(i))));
    }
    return new Node(new Either<>(subNodes, null));
  }

  public Optional<Boolean> compare(Node a, Node b) {
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
      else return compare(na.get(0), nb.get(0)).or(() -> compare(new Node(new Either<>(na.subList(1, na.size()), null)), new Node(new Either<>(nb.subList(1, nb.size()), null))));
    } else if(a.value.isA()) return compare(a, new Node(new Either<>(List.of(b), null)));
    else return compare(new Node(new Either<>(List.of(a), null)), b);
  }

  @Override
  public Object part2() {
    var in = Arrays.stream((day()+"\n[[2]]\n[[6]]").replace("\n\n", "\n").split("\n"))
            .map(s -> node(s, findLevels(s)))
            .sorted((a, b) -> compare(a, b).map(c -> c ? -1 : 1).orElse(0))
            .toList();
    return (in.indexOf(new Node(new Either<>(List.of(new Node(new Either<>(List.of(new Node(new Either<>(null, 2))), null))), null))) + 1) * (in.indexOf(new Node(new Either<>(List.of(new Node(new Either<>(List.of(new Node(new Either<>(null, 6))), null))), null))) + 1);
  }
}

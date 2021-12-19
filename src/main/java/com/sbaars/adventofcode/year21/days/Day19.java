package com.sbaars.adventofcode.year21.days;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

import com.google.common.collect.ArrayListMultimap;
import com.sbaars.adventofcode.common.Loc3D;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;

public class Day19 extends Day2021 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts(6);
//    new Day19().submitPart1();
//    new Day19().submitPart2();
  }

  @Override
  public Object part1() {
    var in = Arrays.stream(day().trim().split("\n\n")).map(Scanner::new).toList();
    for(Scanner s : in) {
      for(int i = 0; i<s.locs.size(); i++){
        for(int j = i+1; j<s.locs.size(); j++) {
          s.mm.put(s.locs().get(i).distance(s.locs.get(j)), Pair.of(s.locs().get(i), s.locs.get(j)));
        }
      }
    }
    List<Pair<Scanner, Scanner>> overlap = new ArrayList<>();
    for(int i = 0; i<in.size(); i++){
      for(int j = i+1; j<in.size(); j++){
        long n = overlap(in.get(i), in.get(j));
        if(n>=12) {
          overlap.add(Pair.of(in.get(i), in.get(j)));
          break;
        }
      }
    }


    return in.stream().flatMap(e -> e.mm.keySet().stream()).distinct().count();
  }

  private long overlap(Scanner a, Scanner b) {
    return a.mm.keySet().stream().filter(e -> b.mm.containsKey(e)).flatMap(e -> a.mm.get(e).stream().flatMap(p -> Stream.of(p.getLeft(), p.getRight()))).distinct().count();
  }

  public Loc3D getRelative(Loc3D l1, Loc3D l2){
    var s1 = l1.toList().stream().map(Math::abs).sorted().toList();
    var s2 = l2.toList().stream().map(Math::abs).sorted().toList();
    var res = Stream.of(abs(s1.get(0) - s2.get(0)), abs(s1.get(1) - s2.get(1)), abs(s1.get(2) - s2.get(2))).sorted().mapToLong(e -> e).toArray();
    return new Loc3D(res);
  }

  @Override
  public Object part2() {
    return "";
  }

  public record Scanner(long id, List<Loc3D> locs, ArrayListMultimap<Double, Pair<Loc3D, Loc3D>> mm) {
    public Scanner(String s){
      this(parseNumAt(12, s), Arrays.stream(s.substring(s.indexOf("\n")+1).split("\n")).map(e -> Arrays.stream(e.split(",")).mapToLong(Long::parseLong).toArray()).map(Loc3D::new).toList(), ArrayListMultimap.create());
    }
  }

  public boolean isDetectedSame(Loc3D l1, Loc3D l2){
    return l1.toList().stream().map(Math::abs).sorted().toList().equals(l2.toList().stream().map(Math::abs).sorted().toList());
  }

  static int parseNumAt(int i, String s){
    if(!isNum(s.charAt(i))) return -1;
    int j = i;
    for(; isNum(s.charAt(j)); j++);
    for(; isNum(s.charAt(i)); i--);
    return parseInt(s.substring(i+1, j));
  }

  static boolean isNum(char c){
    return c >= '0' && c <= '9';
  }
}

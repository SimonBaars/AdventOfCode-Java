package com.sbaars.adventofcode.year21.days;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

import com.google.common.collect.ArrayListMultimap;
import com.sbaars.adventofcode.common.Loc3D;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

public class Day19 extends Day2021 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts(5);
//    new Day19().submitPart1();
//    new Day19().submitPart2();
  }

  @Override
  public Object part1() {
    var in = Arrays.stream(day().trim().split("\n\n")).map(Scanner::new).toList();
    for(Scanner s : in) {
      for(int i = 0; i<s.locs.size(); i++){
        for(int j = i+1; j<s.locs.size(); j++) {
          s.mm.put(getRelative(s.locs().get(i), s.locs.get(j)), Pair.of(s.locs().get(i), s.locs.get(j)));
        }
      }
    }

    Set<Loc3D> unique = new HashSet<>();
    for(Scanner s : in) {
      s.locs.forEach(e -> unique.add(getRelative(e)));
    }
    System.out.println(Arrays.stream(in).flatMap(e -> e.locs.stream()).count());
    return unique.size();
  }

  public String getRelatives(Loc3D l1){
    System.out.println(Arrays.toString(List.of(abs(l1.x-l1.y), abs(l1.y - l1.z), abs(l1.z - l1.x)).stream().sorted().toArray()));
    return Arrays.toString(List.of(abs(l1.x-l1.y), abs(l1.y - l1.z), abs(l1.z - l1.x)).stream().sorted().toArray());
  }

  public Loc3D getRelative(Loc3D l1, Loc3D l2){
    var s1 = l1.toList().stream().map(Math::abs).sorted().toList();
    var s2 = l2.toList().stream().map(Math::abs).sorted().toList();
    return new Loc3D(abs(s1.get(0) - s2.get(0)), abs(s1.get(1) - s2.get(1)), abs(s1.get(2) - s2.get(2)) );
  }

  @Override
  public Object part2() {
    return "";
  }

  public record Scanner(long id, List<Loc3D> locs, ArrayListMultimap<Loc3D, Pair<Loc3D, Loc3D>> mm) {
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

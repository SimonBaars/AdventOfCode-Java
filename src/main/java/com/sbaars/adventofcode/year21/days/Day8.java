package com.sbaars.adventofcode.year21.days;

import static java.util.Arrays.asList;

import com.sbaars.adventofcode.common.StringTools;
import com.sbaars.adventofcode.year21.Day2021;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 extends Day2021 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) throws IOException {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(e -> e.split("\\|")[1]).flatMap(e -> Stream.of(e.split(" ")))
        .filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7)
        .count();
  }

  @Override
  public Object part2() {
    return dayStream()
        .filter(e -> !e.isBlank()).map(e -> e.split("\\|"))
        .map(e -> new String[][]{e[0].split(" "), Arrays.stream(e[1].split(" ")).filter(f -> !f.isBlank()).toArray(String[]::new)})
        .mapToInt(line -> Integer.parseInt(Stream.of(line[1]).map(e -> getNum(line[0], e)).collect(Collectors.joining())))
        .sum();

  }

  private String getNum(String[] line, String w) {
    if(w.length() == 2){
      return "1";
    } else if(w.length() == 3) {
      return "7";
    } else if(w.length() == 4) {
      return "4";
    } else if(w.length() == 7) {
      return "8";
    } else if(w.length() == 6) {
      String bottomLeft = getBottomLeft(line);
      if(w.contains(bottomLeft)) {
        if(getMiddle(line, w)){
          return "0";
        } else return "6";
      } else return "9";
    } else if(w.length() == 5) {
      String bottomLeft = getBottomLeft(line);
      if(w.contains(bottomLeft)) {
        return "2";
      }
      String topRight = getTopRight(line);
      if(w.contains(topRight)){
        return "3";
      } else return "5";
    } else throw new IllegalStateException("Unrecognized word "+w);
  }

  private String getBottomLeft(String[] line){
    List<String> l = asList(line);
    List<Integer> possible = new ArrayList<>(asList((int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g'));
    l.stream().filter(e -> e.length() >=2 && e.length() <=4).forEach(e -> e.chars().forEach(i -> possible.remove(Integer.valueOf(i))));
    if(possible.size()>1){
      return Character.toString(getPotentialUniques(line).stream().filter(possible::contains).findAny().get());
    }
    return Character.toString(possible.get(0));
  }

  public Set<Integer> getPotentialUniques(String[] line){
    return Stream.of(line).filter(e -> e.length() == 6).map(this::difference).map(e -> (int)e.toCharArray()[0]).collect(Collectors.toSet());
  }

  private String getTopRight(String[] line){
    List<String> l = asList(line);
    List<Integer> possible = new ArrayList<>(asList((int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g'));
    l.stream().filter(e -> e.length() >=2 && e.length() <=4)
        .forEach(e -> difference(e).chars().forEach(i -> possible.remove(Integer.valueOf(i))));
    if(possible.size()>1){
      return Character.toString(getPotentialUniques(line).stream().filter(possible::contains).findAny().get());
    }
    return Character.toString(possible.get(0));
  }

  private String difference(String a) {
    return StringTools.removeAll("abcdefg", a);
  }

  private boolean getMiddle(String[] line, String a){
    return Stream.of(line).filter(e -> e.length() == 2 || e.length() == 3).allMatch(e -> subset(e, a));
  }

  private boolean subset(String a, String b){
    return a.chars().allMatch(c -> b.chars().anyMatch(d -> d == c));
  }
}

package com.sbaars.adventofcode.year21.days;

import static com.google.common.collect.ObjectArrays.concat;
import static java.util.Arrays.asList;

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
//    System.in.read();
//    new Day8().submitPart1();
//    new Day8().submitPart2();
  }

  @Override
  public Object part1() {
    //System.out.println(Arrays.toString(dayStream().flatMap(e -> Stream.of(e.split(" "))).filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7).toArray()));
    return dayStream().filter(e -> !e.isBlank()).map(e -> e.split("\\|")[1]).flatMap(e -> Stream.of(e.split(" "))).filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7).count();
  }

  @Override
  public Object part2() {
    return dayStream()
//        .peek(System.out::println)
        .filter(e -> !e.isBlank()).map(e -> e.split("\\|"))
        .map(e -> new String[][]{e[0].split(" "), Arrays.stream(e[1].split(" ")).filter(f -> !f.isBlank()).toArray(String[]::new)})
//        .peek(e -> System.out.println(Arrays.toString(e[1])))
        .mapToInt(line -> Integer.parseInt(Stream.of(line[1]).map(e -> getNum(concat(line[0], line[1], String.class), e)).collect(Collectors.joining())))
//        .peek(e -> System.out.println(e))
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
      String unique1 = getUnique1(line);
      if(w.contains(unique1)) {
        return "6";
      } else return "9";
    } else if(w.length() == 5) {
      String unique1 = getUnique1(line);
      if(w.contains(unique1)) {
        return "2";
      }
      String unique2 = getUnique2(line);
      if(w.contains(unique2)){
        return "3";
      } else return "5";
    } else throw new IllegalStateException("Unrecognized word "+w);
  }

  private String getUnique1(String[] line){
    List<String> l = asList(line);
    List<Integer> possible = new ArrayList<>(asList((int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g'));
    l.stream().filter(e -> e.length() >=2 && e.length() <=4).forEach(e -> e.chars().forEach(i -> possible.remove(Integer.valueOf(i))));
//    System.out.println(Arrays.toString(possible.stream().map(e -> (char)e.intValue()).toArray()));
    if(possible.size()>1){
//      System.out.println(Arrays.toString(getPotentialUniques(line).stream().filter(e -> possible.contains(e)).toArray()));
      return Character.toString(getPotentialUniques(line).stream().filter(e -> possible.contains(e)).findAny().get());
    }
    return Character.toString(possible.get(0));
  }

  public Set<Integer> getPotentialUniques(String[] line){
    return Stream.of(line).filter(e -> e.length() == 6).map(e -> difference(e)).map(e -> (int)e.toCharArray()[0]).collect(Collectors.toSet());
  }

  private String getUnique2(String[] line){
    List<String> l = asList(line);
    List<Integer> possible = new ArrayList<>(asList((int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g'));
    l.stream().filter(e -> e.length() >=2 && e.length() <=4)
//        .peek(e -> System.out.println(e+", "+difference(e)))
        .forEach(e -> difference(e).chars().forEach(i -> possible.remove(Integer.valueOf(i))));
//    System.out.println(Arrays.toString(possible.stream().map(e -> (char)e.intValue()).toArray()));
    if(possible.size()>1){
//      System.out.println(Arrays.toString(getPotentialUniques(line).stream().filter(e -> possible.contains(e)).toArray()));
      return Character.toString(getPotentialUniques(line).stream().filter(e -> possible.contains(e)).findAny().get());
    }
    return Character.toString(possible.get(0));
  }

  private String difference(String a){
    var arr = new ArrayList<>(asList('a', 'b', 'c', 'd', 'e', 'f', 'g'));
    arr.removeAll(a.chars().mapToObj(c -> (char) c).toList());
    return arr.stream().map(e -> e.toString()).collect(Collectors.joining());
  }
}

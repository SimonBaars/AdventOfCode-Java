package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.IntArray;
import com.sbaars.adventofcode.util.StringUtils;
import com.sbaars.adventofcode.year21.Day2021;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.StringUtils.charSubset;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class Day8 extends Day2021 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  @Override
  public Object part1() {
    return readInput()
        .flatMap(e -> stream(e[1]))
        .filter(e -> e.length() == 2 || e.length() == 3 || e.length() == 4 || e.length() == 7)
        .count();
  }

  @Override
  public Object part2() {
    return readInput()
        .mapToInt(line -> Integer.parseInt(Stream.of(line[1]).map(e -> getNum(line[0], e)).collect(Collectors.joining())))
        .sum();
  }

  private Stream<String[][]> readInput() {
    return dayStream()
        .map(e -> e.split("\\|"))
        .map(e -> new String[][]{e[0].split(" "), stream(e[1].split(" ")).filter(f -> !f.isBlank()).toArray(String[]::new)});
  }

  private String getNum(String[] line, String w) {
    return switch (w.length()) {
      case 2 -> "1";
      case 3 -> "7";
      case 4 -> "4";
      case 7 -> "8";
      case 6 -> {
        String bottomLeft = getBottomLeft(line, false);
        if (w.contains(bottomLeft)) {
          if (getMiddle(line, w)) {
            yield "0";
          } else yield "6";
        } else yield "9";
      }
      case 5 -> {
        String bottomLeft = getBottomLeft(line, false);
        if (w.contains(bottomLeft)) {
          yield "2";
        }
        String topRight = getBottomLeft(line, true);
        if (w.contains(topRight)) {
          yield "3";
        } else yield "5";
      }
      default -> throw new IllegalStateException("Unrecognized word " + w);
    };
  }

  private String getBottomLeft(String[] line, boolean mirror) {
    List<String> l = asList(line);
    IntArray possible = new IntArray("abcdefg".chars());
    l.stream().filter(e -> e.length() >= 2 && e.length() <= 4)
        .forEach(e -> (mirror ? difference(e) : e).chars().forEach(possible::removeElement));
    if (possible.size() > 1) {
      return Character.toString(getPotentialUniques(line).stream().filter(possible::contains).findAny().get());
    }
    return Character.toString(possible.get(0));
  }

  public Set<Integer> getPotentialUniques(String[] line) {
    return Stream.of(line).filter(e -> e.length() == 6).map(this::difference).map(e -> (int) e.toCharArray()[0]).collect(Collectors.toSet());
  }

  private String difference(String a) {
    return StringUtils.removeAll("abcdefg", a);
  }

  private boolean getMiddle(String[] line, String a) {
    return Stream.of(line).filter(e -> e.length() == 2 || e.length() == 3).allMatch(e -> charSubset(e, a));
  }
}

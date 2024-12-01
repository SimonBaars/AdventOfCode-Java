package com.sbaars.adventofcode.year24.days;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.sbaars.adventofcode.year24.Day2024;
import static com.sbaars.adventofcode.util.DataMapper.readString;

/**
 * day() is the input of the day as a string
 * dayStream() is the input of the day as a stream of lines
 */

public class Day1 extends Day2024 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  public record Pair(int a, int b) {}

  @Override
  public Object part1() {
    List<Pair> input = parseInput();
    
    List<Integer> leftList = input.stream().map(Pair::a).sorted().toList();
    List<Integer> rightList = input.stream().map(Pair::b).sorted().toList();
    
    return calculateTotalDistance(leftList, rightList);
  }

  @Override
  public Object part2() {
    List<Pair> input = parseInput();
    
    List<Integer> leftList = input.stream().map(Pair::a).toList();
    List<Integer> rightList = input.stream().map(Pair::b).toList();
    
    return calculateSimilarityScore(leftList, rightList);
  }

  private List<Pair> parseInput() {
    return dayStream().filter(s -> !s.isBlank()).map(s -> readString(s, "%i   %i", Pair.class)).toList();
  }

  private int calculateTotalDistance(List<Integer> leftList, List<Integer> rightList) {
    int totalDistance = 0;
    for (int i = 0; i < leftList.size(); i++) {
      totalDistance += Math.abs(leftList.get(i) - rightList.get(i));
    }
    return totalDistance;
  }

  private int calculateSimilarityScore(List<Integer> leftList, List<Integer> rightList) {
    Map<Integer, Long> rightCountMap = rightList.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    
    return leftList.stream()
        .mapToInt(num -> num * rightCountMap.getOrDefault(num, 0L).intValue())
        .sum();
  }
}

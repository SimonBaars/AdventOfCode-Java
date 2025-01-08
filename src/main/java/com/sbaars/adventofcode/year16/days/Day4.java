package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day4 extends Day2016 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  private boolean isRealRoom(String name, String checksum) {
    // Remove dashes and count letter frequencies
    Map<Character, Long> freq = name.replace("-", "").chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

    // Get top 5 most common letters, breaking ties alphabetically
    String calculatedChecksum = freq.entrySet().stream()
        .sorted((a, b) -> {
          int freqCompare = b.getValue().compareTo(a.getValue());
          return freqCompare != 0 ? freqCompare : a.getKey().compareTo(b.getKey());
        })
        .limit(5)
        .map(e -> String.valueOf(e.getKey()))
        .collect(Collectors.joining());

    return calculatedChecksum.equals(checksum);
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(line -> {
          // Parse room string into components
          int checksumStart = line.indexOf('[');
          String checksum = line.substring(checksumStart + 1, line.length() - 1);
          String[] parts = line.substring(0, checksumStart).split("-");
          int sectorId = Integer.parseInt(parts[parts.length - 1]);
          String name = String.join("-", Arrays.copyOf(parts, parts.length - 1));

          return isRealRoom(name, checksum) ? sectorId : 0;
        })
        .mapToInt(Integer::intValue)
        .sum();
  }

  @Override
  public Object part2() {
    return "";
  }
}

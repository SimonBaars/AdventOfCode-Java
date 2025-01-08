package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.stream.Collectors;

public class Day4 extends Day2016 {
  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  private record Room(String name, int sectorId, String checksum) {
    public boolean isReal() {
      Map<Character, Integer> freq = new HashMap<>();
      for (char c : name.replaceAll("-", "").toCharArray()) {
        freq.merge(c, 1, Integer::sum);
      }
      
      String calculatedChecksum = freq.entrySet().stream()
          .sorted((a, b) -> {
            int comp = b.getValue().compareTo(a.getValue());
            return comp != 0 ? comp : a.getKey().compareTo(b.getKey());
          })
          .limit(5)
          .map(e -> String.valueOf(e.getKey()))
          .collect(Collectors.joining());
      
      return calculatedChecksum.equals(checksum);
    }
    
    public String decrypt() {
      StringBuilder result = new StringBuilder();
      for (char c : name.toCharArray()) {
        if (c == '-') {
          result.append(' ');
        } else {
          int shifted = ((c - 'a' + sectorId) % 26) + 'a';
          result.append((char) shifted);
        }
      }
      return result.toString();
    }
  }

  private Room parseRoom(String line) {
    String[] parts = line.split("\\[");
    String checksum = parts[1].substring(0, parts[1].length() - 1);
    String[] nameParts = parts[0].split("-(?=\\d)");
    String name = nameParts[0];
    int sectorId = Integer.parseInt(nameParts[1]);
    return new Room(name, sectorId, checksum);
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(this::parseRoom)
        .filter(Room::isReal)
        .mapToInt(Room::sectorId)
        .sum();
  }

  @Override
  public Object part2() {
    return dayStream()
        .map(this::parseRoom)
        .filter(Room::isReal)
        .filter(room -> room.decrypt().contains("northpole"))
        .findFirst()
        .map(Room::sectorId)
        .orElse(0);
  }
}

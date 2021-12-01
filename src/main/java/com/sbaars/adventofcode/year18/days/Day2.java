package com.sbaars.adventofcode.year18.days;

import static java.util.Optional.empty;

import com.google.common.collect.Sets;
import com.sbaars.adventofcode.year18.Day2018;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day2 extends Day2018 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().submitPart2();
  }

  @Override
  public Object part1() {
    int two = 0, three = 0;
    String[] input = dayStrings();
    for(String i : input){
      List<Integer> counts = i.chars().mapToObj(c -> (i+"_").split(Character.toString(c)).length - 1).toList();
      if(counts.contains(2)) {
        two++;
      }
      if(counts.contains(3)) {
        three++;
      }
    }
    return two * three;
  }

  @Override
  public Object part2() {
    var com = Sets.combinations(dayStream().collect(Collectors.toSet()), 2);
    return com.stream().map(this::compareStrings).filter(Optional::isPresent).map(Optional::get).findFirst().get();
  }

  private Optional<String> compareStrings(Set<String> str) {
    var it = str.iterator();
    String str1 = it.next(), str2 = it.next();
    if (str1.length() != str2.length() || str1.equals(str2)) return empty();
    int differences = 0;
    int diffIndex = -1;
    for (int i = 0; i < str1.length(); i++) {
      if(str1.charAt(i) != str2.charAt(i)) {
        diffIndex = i;
        if (++differences > 1) {
          return empty();
        }
      }
    }
    return Optional.of(new StringBuilder(str1).deleteCharAt(diffIndex).toString());
  }
}

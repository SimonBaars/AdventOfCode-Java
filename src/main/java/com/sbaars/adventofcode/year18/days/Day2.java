package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.List;
import java.util.Optional;

import static com.sbaars.adventofcode.util.AOCUtils.allPairs;
import static java.util.Optional.empty;

public class Day2 extends Day2018 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
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
    return allPairs(dayStream().toList()).map(this::compareStrings).filter(Optional::isPresent).map(Optional::get).findFirst().get();
  }

  private Optional<String> compareStrings(Pair<String, String> str) {
    if (str.a().length() != str.b().length() || str.a().equals(str.b())) return empty();
    int differences = 0;
    int diffIndex = -1;
    for (int i = 0; i < str.a().length(); i++) {
      if(str.a().charAt(i) != str.b().charAt(i)) {
        diffIndex = i;
        if (++differences > 1) {
          return empty();
        }
      }
    }
    return Optional.of(new StringBuilder(str.a()).deleteCharAt(diffIndex).toString());
  }
}

package com.sbaars.adventofcode.year20.days;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.lang.Math.toIntExact;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.function.Predicate;

public class Day2 extends Day2020 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  public static Password mapPassword(String s) {
    return readString(s, "%n-%n %c: %s", Password.class);
  }

  @Override
  public Object part1() {
    return pwCount(Password::isValid);
  }

  private long pwCount(Predicate<Password> validator) {
    return dayStream().map(Day2::mapPassword).filter(validator).count();
  }

  @Override
  public Object part2() {
    return pwCount(Password::isValid2);
  }

  public record Password(long lower, long higher, char character, String password) {

    public boolean isValid() {
      long count = password.chars().filter(c -> c == character).count();
      return count >= lower && count <= higher;
    }

    public boolean isValid2() {
      int char1 = password.charAt(toIntExact(lower - 1));
      int char2 = password.charAt(toIntExact(higher - 1));
      return char1 != char2 && (char1 == character || char2 == character);
    }
  }
}

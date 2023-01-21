package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year15.Day2015;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.sbaars.adventofcode.common.Pair.pair;
import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.IntStream.range;

public class Day4 extends Day2015 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  @Override
  public Object part1() {
    return solution("00000");
  }

  @Override
  public Object part2() {
    return solution("000000");
  }

  private int solution(String prefix) {
    String in = day().trim();
    return range(0, MAX_VALUE)
            .mapToObj(i -> pair(i, md5(in + i)))
            .filter(s -> s.b().startsWith(prefix))
            .map(Pair::a)
            .findFirst()
            .get();
  }

  public String md5(String md5) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] array = md.digest(md5.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : array) {
        sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException ignored) {
    }
    throw new IllegalStateException("Unable to hash "+md5);
  }
}

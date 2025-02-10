package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import com.sbaars.adventofcode.common.MD5;

import java.util.Arrays;

public class Day5 extends Day2016 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  @Override
  public Object part1() {
    String doorId = day().trim();
    StringBuilder password = new StringBuilder();
    int index = 0;

    while (password.length() < 8) {
      byte[] hash = MD5.hash(doorId + index);
      if (MD5.hasLeadingZeros(hash, 5)) {
        password.append(MD5.toHexChar(hash[2] & 0x0F));
      }
      index++;
    }
    return password.toString();
  }

  @Override
  public Object part2() {
    String doorId = day().trim();
    char[] password = new char[8];
    Arrays.fill(password, '_');
    int filled = 0;
    int index = 0;

    while (filled < 8) {
      byte[] hash = MD5.hash(doorId + index);
      if (MD5.hasLeadingZeros(hash, 5)) {
        int pos = hash[2] & 0x0F;
        if (pos < 8 && password[pos] == '_') {
          password[pos] = MD5.toHexChar((hash[3] & 0xF0) >>> 4);
          filled++;
        }
      }
      index++;
    }
    return new String(password);
  }
}

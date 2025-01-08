package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

public class Day16 extends Day2016 {
  private static final int DISK_LENGTH = 272;

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  private String generateData(String a) {
    StringBuilder b = new StringBuilder(a).reverse();
    for (int i = 0; i < b.length(); i++) {
      b.setCharAt(i, b.charAt(i) == '0' ? '1' : '0');
    }
    return a + "0" + b;
  }

  private String calculateChecksum(String data) {
    StringBuilder checksum = new StringBuilder();
    for (int i = 0; i < data.length(); i += 2) {
      checksum.append(data.charAt(i) == data.charAt(i + 1) ? '1' : '0');
    }
    return checksum.length() % 2 == 0 ? calculateChecksum(checksum.toString()) : checksum.toString();
  }

  private String fillDisk(String input, int length) {
    String data = input;
    while (data.length() < length) {
      data = generateData(data);
    }
    return calculateChecksum(data.substring(0, length));
  }

  @Override
  public Object part1() {
    return fillDisk(day().trim(), DISK_LENGTH);
  }

  @Override
  public Object part2() {
    return "";
  }
}

package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class Day5 extends Day2016 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  private String getMD5Hash(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexString = new StringBuilder();
      for (byte b : digest) {
        hexString.append(String.format("%02x", b & 0xff));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object part1() {
    String doorId = day().trim();
    StringBuilder password = new StringBuilder();
    long index = 0;

    while (password.length() < 8) {
      String hash = getMD5Hash(doorId + index);
      if (hash.startsWith("00000")) {
        char nextChar = hash.charAt(5);
        password.append(nextChar);
      }
      index++;
    }

    return password.toString();
  }

  @Override
  public Object part2() {
    return "";
  }
}

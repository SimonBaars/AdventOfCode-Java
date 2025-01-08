package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
      byte[] digest = md.digest(input.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : digest) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Object part1() {
    String doorId = day();
    StringBuilder password = new StringBuilder();
    int index = 0;

    while (password.length() < 8) {
      String hash = getMD5Hash(doorId + index);
      if (hash.startsWith("00000")) {
        password.append(hash.charAt(5));
      }
      index++;
    }

    return password.toString();
  }

  @Override
  public Object part2() {
    String doorId = day();
    char[] password = new char[8];
    Arrays.fill(password, '_');
    int index = 0;
    int found = 0;

    while (found < 8) {
      String hash = getMD5Hash(doorId + index);
      if (hash.startsWith("00000")) {
        int pos = hash.charAt(5) - '0';
        if (pos >= 0 && pos < 8 && password[pos] == '_') {
          password[pos] = hash.charAt(6);
          found++;
        }
      }
      index++;
    }

    return new String(password);
  }
}

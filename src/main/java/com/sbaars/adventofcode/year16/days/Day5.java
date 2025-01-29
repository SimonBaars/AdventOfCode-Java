package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

import java.nio.charset.StandardCharsets;
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

  @Override
  public Object part1() {
    String doorId = day().trim();
    StringBuilder password = new StringBuilder();
    int index = 0;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      while (password.length() < 8) {
        String input = doorId + index;
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        String hash = bytesToHex(digest);
        if (hash.startsWith("00000")) {
          password.append(hash.charAt(5));
        }
        index++;
      }
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
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
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      while (filled < 8) {
        String input = doorId + index;
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        String hash = bytesToHex(digest);
        if (hash.startsWith("00000") && hash.length() >= 7) {
          char posChar = hash.charAt(5);
          if (posChar >= '0' && posChar <= '7') {
            int pos = posChar - '0';
            if (password[pos] == '_') {
              char c = hash.charAt(6);
              password[pos] = c;
              filled++;
            }
          }
        }
        index++;
      }
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return new String(password);
  }

  private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }
}

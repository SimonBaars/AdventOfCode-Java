package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Day2016 {
  private static final int REQUIRED_KEYS = 64;
  private static final int WINDOW_SIZE = 1000;

  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  private String getMD5Hash(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] digest = md.digest(input.getBytes());
      StringBuilder hexString = new StringBuilder();
      for (byte b : digest) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) hexString.append('0');
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private Character findTriple(String hash) {
    for (int i = 0; i < hash.length() - 2; i++) {
      if (hash.charAt(i) == hash.charAt(i + 1) && 
          hash.charAt(i) == hash.charAt(i + 2)) {
        return hash.charAt(i);
      }
    }
    return null;
  }

  private boolean hasFiveInARow(String hash, char c) {
    String target = String.valueOf(c).repeat(5);
    return hash.contains(target);
  }

  private int findIndexOf64thKey() {
    String salt = day().trim();
    Map<Integer, String> hashes = new HashMap<>();
    List<Integer> keys = new ArrayList<>();
    int index = 0;

    while (keys.size() < REQUIRED_KEYS) {
      String hash = hashes.computeIfAbsent(index, i -> getMD5Hash(salt + i));
      Character triple = findTriple(hash);

      if (triple != null) {
        // Look ahead for quintuple
        for (int j = index + 1; j <= index + WINDOW_SIZE; j++) {
          String nextHash = hashes.computeIfAbsent(j, i -> getMD5Hash(salt + i));
          if (hasFiveInARow(nextHash, triple)) {
            keys.add(index);
            break;
          }
        }
      }
      index++;
    }

    return keys.get(REQUIRED_KEYS - 1);
  }

  @Override
  public Object part1() {
    return findIndexOf64thKey();
  }

  @Override
  public Object part2() {
    return "";
  }
}

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
        byte[] digest = md.digest((doorId + index).getBytes(StandardCharsets.UTF_8));

        // Check if first 20 bits (5 hex zeroes) are zero
        // digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xF0) == 0
        if (digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xF0) == 0) {
          // The 6th hex character is the low nibble of digest[2]
          int nib5 = digest[2] & 0x0F;
          password.append(toHexChar(nib5));
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
        byte[] digest = md.digest((doorId + index).getBytes(StandardCharsets.UTF_8));
        // Same check as above
        if (digest[0] == 0 && digest[1] == 0 && (digest[2] & 0xF0) == 0) {
          // 6th hex character is nib5 (low nibble of digest[2]), 7th is nib6 (high nibble of digest[3])
          int nib5 = digest[2] & 0x0F;            // position
          int nib6 = (digest[3] & 0xF0) >>> 4;    // actual character

          if (nib5 < 8 && password[nib5] == '_') {
            password[nib5] = toHexChar(nib6);
            filled++;
          }
        }
        index++;
      }
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    return new String(password);
  }

  private static char toHexChar(int nibble) {
    return (char) (nibble < 10 ? ('0' + nibble) : ('a' + (nibble - 10)));
  }
}

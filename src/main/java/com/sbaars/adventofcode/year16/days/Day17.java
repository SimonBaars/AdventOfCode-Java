package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Queue;

public class Day17 extends Day2016 {
  private static final int GRID_SIZE = 4;
  private static final char[] DIRECTIONS = {'U', 'D', 'L', 'R'};
  private static final int[][] MOVES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
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

  private boolean isDoorOpen(char c) {
    return c >= 'b' && c <= 'f';
  }

  private record State(int x, int y, String path) {}

  private String findShortestPath() {
    String passcode = day().trim();
    Queue<State> queue = new LinkedList<>();
    queue.add(new State(0, 0, ""));

    while (!queue.isEmpty()) {
      State current = queue.poll();

      if (current.x == GRID_SIZE - 1 && current.y == GRID_SIZE - 1) {
        return current.path;
      }

      String hash = getMD5Hash(passcode + current.path);
      for (int i = 0; i < 4; i++) {
        if (isDoorOpen(hash.charAt(i))) {
          int newX = current.x + MOVES[i][0];
          int newY = current.y + MOVES[i][1];

          if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE) {
            queue.add(new State(newX, newY, current.path + DIRECTIONS[i]));
          }
        }
      }
    }

    return "NO PATH FOUND";
  }

  private int findLongestPath() {
    String passcode = day().trim();
    Queue<State> queue = new LinkedList<>();
    queue.add(new State(0, 0, ""));
    int longestPath = -1;

    while (!queue.isEmpty()) {
      State current = queue.poll();

      if (current.x == GRID_SIZE - 1 && current.y == GRID_SIZE - 1) {
        longestPath = Math.max(longestPath, current.path.length());
        continue;
      }

      String hash = getMD5Hash(passcode + current.path);
      for (int i = 0; i < 4; i++) {
        if (isDoorOpen(hash.charAt(i))) {
          int newX = current.x + MOVES[i][0];
          int newY = current.y + MOVES[i][1];

          if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE) {
            queue.add(new State(newX, newY, current.path + DIRECTIONS[i]));
          }
        }
      }
    }

    return longestPath;
  }

  @Override
  public Object part1() {
    return findShortestPath();
  }

  @Override
  public Object part2() {
    return findLongestPath();
  }
}

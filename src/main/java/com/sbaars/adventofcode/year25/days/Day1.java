package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

public class Day1 extends Day2025 {
  public Day1() {
    super(1);
  }

  public static void main(String[] args) {
    new Day1().printParts();
  }

  record Rotation(char direction, int distance) {
    static Rotation parse(String line) {
      char dir = line.charAt(0);
      int dist = Integer.parseInt(line.substring(1));
      return new Rotation(dir, dist);
    }
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(Rotation::parse)
        .reduce(new State(50, 0), 
                (state, rotation) -> state.applyRotation(rotation),
                (s1, s2) -> s2)
        .zeroCount;
  }

  @Override
  public Object part2() {
    return dayStream()
        .map(Rotation::parse)
        .reduce(new State(50, 0), 
                (state, rotation) -> state.applyRotationWithClicks(rotation),
                (s1, s2) -> s2)
        .zeroCount;
  }

  record State(int position, int zeroCount) {
    State applyRotation(Rotation rotation) {
      int newPosition;
      if (rotation.direction == 'L') {
        newPosition = (position - rotation.distance) % 100;
        if (newPosition < 0) newPosition += 100;
      } else { // R
        newPosition = (position + rotation.distance) % 100;
      }
      
      int newCount = zeroCount + (newPosition == 0 ? 1 : 0);
      return new State(newPosition, newCount);
    }
    
    State applyRotationWithClicks(Rotation rotation) {
      int newPosition;
      int clickCount = 0;
      
      if (rotation.direction == 'L') {
        newPosition = (position - rotation.distance) % 100;
        if (newPosition < 0) newPosition += 100;
        
        // Count how many times we pass through 0
        // From position going left distance clicks
        for (int i = 1; i <= rotation.distance; i++) {
          int pos = (position - i) % 100;
          if (pos < 0) pos += 100;
          if (pos == 0) clickCount++;
        }
      } else { // R
        newPosition = (position + rotation.distance) % 100;
        
        // Count how many times we pass through 0
        for (int i = 1; i <= rotation.distance; i++) {
          int pos = (position + i) % 100;
          if (pos == 0) clickCount++;
        }
      }
      
      return new State(newPosition, zeroCount + clickCount);
    }
  }
}

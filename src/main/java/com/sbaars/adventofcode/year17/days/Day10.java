package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 extends Day2017 {
  private static final int[] SUFFIX = {17, 31, 73, 47, 23};
  
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }
  
  private void processLength(int[] list, int length, int currentPos, int skipSize) {
    // Reverse the sublist
    for (int i = 0; i < length / 2; i++) {
      int pos1 = (currentPos + i) % list.length;
      int pos2 = (currentPos + length - 1 - i) % list.length;
      int temp = list[pos1];
      list[pos1] = list[pos2];
      list[pos2] = temp;
    }
  }
  
  private String calculateKnotHash(String input) {
    // Convert input to ASCII and add suffix
    int[] lengths = input.chars()
        .mapToObj(Integer::valueOf)
        .collect(Collectors.toList())
        .stream()
        .mapToInt(Integer::intValue)
        .toArray();
    
    int[] fullLengths = Arrays.copyOf(lengths, lengths.length + SUFFIX.length);
    System.arraycopy(SUFFIX, 0, fullLengths, lengths.length, SUFFIX.length);
    
    // Initialize list
    int[] list = IntStream.range(0, 256).toArray();
    int currentPos = 0;
    int skipSize = 0;
    
    // Process 64 rounds
    for (int round = 0; round < 64; round++) {
      for (int length : fullLengths) {
        processLength(list, length, currentPos, skipSize);
        currentPos = (currentPos + length + skipSize) % list.length;
        skipSize++;
      }
    }
    
    // Calculate dense hash
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < 16; i++) {
      int xor = 0;
      for (int j = 0; j < 16; j++) {
        xor ^= list[i * 16 + j];
      }
      result.append(String.format("%02x", xor));
    }
    
    return result.toString();
  }

  @Override
  public Object part1() {
    int[] list = IntStream.range(0, 256).toArray();
    int[] lengths = Arrays.stream(day().trim().split(",")).mapToInt(Integer::parseInt).toArray();
    int currentPos = 0;
    int skipSize = 0;
    
    for (int length : lengths) {
      processLength(list, length, currentPos, skipSize);
      currentPos = (currentPos + length + skipSize) % list.length;
      skipSize++;
    }
    
    return list[0] * list[1];
  }

  @Override
  public Object part2() {
    return calculateKnotHash(day().trim());
  }
}

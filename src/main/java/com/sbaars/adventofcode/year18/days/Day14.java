package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;

import java.util.ArrayList;
import java.util.List;

public class Day14 extends Day2018 {
  public Day14() {
    super(14);
  }

  public static void main(String[] args) {
    new Day14().printParts();
  }

  @Override
  public Object part1() {
    return getScores(dayNumber());
  }

  @Override
  public Object part2() {
    return findScoreSequence(dayNumber());
  }

  public String getScores(long numRecipes) {
    List<Integer> scores = startScores();
    int elf1 = 0;
    int elf2 = 1;

    while (scores.size() < numRecipes + 10) {
      int newScore = scores.get(elf1) + scores.get(elf2);
      if (newScore >= 10) {
        scores.add(newScore / 10);
      }
      scores.add(newScore % 10);

      elf1 = (elf1 + scores.get(elf1) + 1) % scores.size();
      elf2 = (elf2 + scores.get(elf2) + 1) % scores.size();
    }

    StringBuilder sb = new StringBuilder();
    for (long i = numRecipes; i < numRecipes + 10; i++) {
      sb.append(scores.get(Math.toIntExact(i)));
    }
    return sb.toString();
  }

  public int findScoreSequence(long numRecipes) {
    List<Integer> scores = startScores();
    int elf1 = 0;
    int elf2 = 1;

    String sequence = String.valueOf(numRecipes);
    int[] seq = sequence.chars().map(c -> c - '0').toArray();
    int seqIndex = 0;

    while (true) {
      int newScore = scores.get(elf1) + scores.get(elf2);
      if (newScore >= 10) {
        scores.add(newScore / 10);
        if (scores.get(scores.size() - 1) == seq[seqIndex]) {
          seqIndex++;
          if (seqIndex == seq.length) {
            return scores.size() - seq.length;
          }
        } else {
          seqIndex = 0;
        }
      }

      scores.add(newScore % 10);
      if (scores.get(scores.size() - 1) == seq[seqIndex]) {
        seqIndex++;
        if (seqIndex == seq.length) {
          return scores.size() - seq.length;
        }
      } else {
        seqIndex = 0;
      }

      elf1 = (elf1 + scores.get(elf1) + 1) % scores.size();
      elf2 = (elf2 + scores.get(elf2) + 1) % scores.size();
    }
  }

  private List<Integer> startScores() {
    List<Integer> scores = new ArrayList<>();
    scores.add(3);
    scores.add(7);
    return scores;
  }
}

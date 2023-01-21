package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.year22.days.Day2.Outcome.*;
import static com.sbaars.adventofcode.year22.days.Day2.Shape.*;

public class Day2 extends Day2022 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) throws IOException {
    new Day2().printParts();
  }

  enum Shape {ROCK, PAPER, SCISSOR}

  ;

  enum Outcome {DRAW, WIN, LOSS}

  ;

  public record Game(String a, String b) {
    private Shape getShape(String s) {
      return switch (s) {
        case "A", "X" -> ROCK;
        case "B", "Y" -> PAPER;
        case "C", "Z" -> SCISSOR;
        default -> throw new RuntimeException(s);
      };
    }

    private Shape choose(Shape s, Outcome desired) {
      return Shape.values()[(s.ordinal() + desired.ordinal()) % Shape.values().length];
    }

    private long getScore1() {
      return getScore(getShape(b));
    }

    private long getScore(Shape sb) {
      Shape sa = getShape(a);
      long baseScore = sb.ordinal() + 1;
      return switch (calculateOutcome(sa, sb)) {
        case LOSS -> baseScore;
        case WIN -> baseScore + 6;
        case DRAW -> baseScore + 3;
      };
    }

    private Outcome calculateOutcome(Shape sa, Shape sb) {
      if (sa == sb) {
        return DRAW;
      } else if (sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)) {
        return LOSS;
      }
      return WIN;
    }

    private long getScore2() {
      return getScore(choose(getShape(a), b.equals("X") ? LOSS : b.equals("Y") ? Outcome.DRAW : WIN));
    }
  }

  @Override
  public Object part1() {
    return input().mapToLong(Game::getScore1).sum();
  }

  @Override
  public Object part2() {
    return input().mapToLong(Game::getScore2).sum();
  }

  private Stream<Game> input() {
    return dayStream().map(String::trim).map(s -> readString(s, "%s %s", Game.class));
  }
}

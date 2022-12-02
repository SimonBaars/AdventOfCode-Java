package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day2 extends Day2022 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) throws IOException {
    Day d = new Day2();
    d.downloadIfNotDownloaded();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  enum Shape {ROCK, PAPER, SCISSOR};
  enum Outcome {DRAW, WIN, LOOSE};

  public record Game(String a, String b) {
    private Shape getShape(String s){
      return switch (s) {
        case "A", "X" -> Shape.ROCK;
        case "B", "Y" -> Shape.PAPER;
        case "C", "Z" -> Shape.SCISSOR;
        default -> throw new RuntimeException(s);
      };
    }

    private Shape choose(Shape s, Outcome desired) {
      return Shape.values()[(s.ordinal() + desired.ordinal()) % Shape.values().length];
    }

    private long getScore() {
      Shape sa = getShape(a);
      Shape sb = getShape(b);
      long baseScore = sb.ordinal()+1;
//      ?System.out.println(baseScore +", "+sa+", "+sb+", draw="+(sa == sb)+", win="+(sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)));
      if(sa == sb) { //draw
        return baseScore + 3;
      } else if(sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)) {//loss
        return baseScore;
      } else return baseScore + 6; //loss
    }

    private long getScore2() {
      Shape sa = getShape(a);
      Shape sb = choose(sa, b.equals("X")?Outcome.LOOSE:b.equals("Y")?Outcome.DRAW:Outcome.WIN);
      long baseScore = sb.ordinal()+1;
//      System.out.println(baseScore +", "+sa+", "+sb+", draw="+(sa == sb)+", win="+(sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)));
      if(sa == sb) { //draw
        return baseScore + 3;
      } else if(sa.ordinal() == ((sb.ordinal() + 1) % Shape.values().length)) {//loss
        return baseScore;
      } else return baseScore + 6; //loss
    }
  }

  @Override
  public Object part1() {
    return dayStream().map(String::trim).map(s -> readString(s, "%s %s", Game.class)).mapToLong(Game::getScore).sum();
  }

  @Override
  public Object part2() {
    return dayStream().map(String::trim).map(s -> readString(s, "%s %s", Game.class)).mapToLong(Game::getScore2).sum();
  }
}

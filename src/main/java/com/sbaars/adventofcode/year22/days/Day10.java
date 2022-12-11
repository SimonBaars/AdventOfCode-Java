package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 extends Day2022 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
    new Day10().part3();
  }

  @Override
  public Object part1() {
    return performOp(dayStrings(), this::signalStrength).mapToLong(e -> e).sum();
  }

  @Override
  public Object part2() {
    return performOp(dayStrings(), this::getPixel).collect(Collectors.joining());
  }

  private long signalStrength(long cycle, long x) {
    return (cycle+20) % 40 == 0 ? cycle*x : 0;
  }

  private String getPixel(long cycle, long x) {
    long i = (cycle -1) % 40;
    return (i == 0 ? "\n" : "") + (List.of(x -1, x, x +1).contains(i) ? "██" : "░░");
  }

  private<T> Stream<T> performOp(String[] input, BiFunction<Long, Long, T> func) {
    long cycle = 1;
    long x  = 1;
    List<T> res = new ArrayList<>();
    for(String op : input) {
      res.add(func.apply(cycle, x));
      if(op.startsWith("addx")) {
        cycle++;
        res.add(func.apply(cycle, x));
        x+=Long.parseLong(op.substring(5));
      }
      cycle++;
    }
    return res.stream();
  }

  // Write any arbitrary String to an ascii art program
  public Object part3() {
    String[] input = genInput("SIMON");
    return performOp(input, this::getPixel).collect(Collectors.joining());
  }

  public String[] genInput(String message) {
    int width = 40;
    int height = 12;
    boolean[][] desiredPixels = new boolean[height][width];
    BufferedImage bufferedImage = new BufferedImage(
            width, height,
            BufferedImage.TYPE_INT_RGB);
    Graphics graphics = bufferedImage.getGraphics();
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics2D.drawString(message, 0, 10);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        desiredPixels[y][x] = bufferedImage.getRGB(x, y) > -7777216;
      }
    }
    return "".split("\n");
  }
}

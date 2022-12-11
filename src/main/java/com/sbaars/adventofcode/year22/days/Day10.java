package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.util.FetchInput;
import com.sbaars.adventofcode.year22.Day2022;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
    System.out.println(new Day10().part3());
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
    long x = 1;
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
    String message = " ☆☺☆ ";
    String[] input = genInput(message);
    FetchInput.writeFile(new File("src/main/resources/2022-day10-part3/"+message+".txt"), String.join("\n", input));
    return "Part 3: "+performOp(input, this::getPixel).collect(Collectors.joining());
  }

  public String[] genInput(String message) {
    int width = 40;
    int height = 9;
    boolean[] desiredPixels = convertStringToAscii(message, width, height);
    return generateInstructions(width, height, desiredPixels).toArray(String[]::new);
  }

  private static List<String> generateInstructions(int width, int height, boolean[] desiredPixels) {
    List<String> output = new ArrayList<>();
    int x = 0;
    for(int cycle = 0; cycle<(width * height)-2; cycle+=2) {
      int desiredX;
      if(desiredPixels[cycle+2] && desiredPixels[cycle+3]){
        desiredX = cycle+1;
      } else if(!desiredPixels[cycle+2] && desiredPixels[cycle+3]) {
        desiredX = cycle+3;
      } else if(desiredPixels[cycle+2] && !desiredPixels[cycle+3]) {
        desiredX = cycle;
      } else {
        desiredX = cycle+4;
      }
      desiredX = desiredX - (((cycle+2) / width)* width);
      output.add("addx "+(desiredX-x));
      x = desiredX;
    }
    output.add("noop");
    output.add("noop");
    return output;
  }

  private static boolean[] convertStringToAscii(String message, int width, int height) {
    boolean[] desiredPixels = new boolean[height * width];
    BufferedImage bufferedImage = new BufferedImage(
            width, height,
            BufferedImage.TYPE_INT_RGB);
    Graphics graphics = bufferedImage.getGraphics();
    Graphics2D graphics2D = (Graphics2D) graphics;
    graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics2D.drawString(message, 0, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        desiredPixels[(y* width)+x] = bufferedImage.getRGB(x, y) > -7777216;
      }
    }
    return desiredPixels;
  }
}

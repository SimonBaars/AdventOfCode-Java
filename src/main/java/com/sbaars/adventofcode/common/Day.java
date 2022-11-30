package com.sbaars.adventofcode.common;

import com.sbaars.adventofcode.util.FetchInput;
import com.sbaars.adventofcode.util.Submit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.apache.commons.io.FileUtils.readFileToString;

public abstract class Day {
  public static final String DEFAULT_DELIMITER = "\n";
  protected final int year;
  protected final int day;
  protected int example = 0;

  public Day(int year, int day) {
    this.year = year;
    this.day = day;
  }

  public static String getResourceAsString(String resource) {
    try {
      return readFileToString(getResource(resource));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static File getResource(String path) {
    return new File("src/main/resources/"+path);
  }

  private String getDayPath() {
    boolean b = example != 0;
    return year + (b ? "-examples" : "") + "/day" + day + (b ? "-" + example : "") + ".txt";
  }

  public abstract Object part1();

  public abstract Object part2();

  public void printParts() {
    System.out.println("Part 1: " + part1());
    System.out.println("Part 2: " + part2());
  }

  public void printParts(int example) {
    this.example = example;
    System.out.println("Part 1: " + part1());
    System.out.println("Part 2: " + part2());
  }

  public void submitPart1() {
    new Submit().submit(part1(), year, day, 1);
  }

  public void submitPart2() {
    new Submit().submit(part2(), year, day, 2);
  }

  protected String day() {
    return getResourceAsString(getDayPath());
  }

  protected String[] dayStrings() {
    return dayStrings(DEFAULT_DELIMITER);
  }

  protected String[] dayStrings(String delimiter) {
    return Arrays.stream(day().split(delimiter)).toArray(String[]::new);
  }

  protected Stream<String> dayStream() {
    return dayStream(DEFAULT_DELIMITER);
  }

  protected Stream<String> dayStream(String delimiter) {
    return Arrays.stream(day().split(delimiter));
  }

  protected IntStream dayIntStream() {
    return dayIntStream(DEFAULT_DELIMITER);
  }

  protected IntStream dayIntStream(String delimiter) {
    return Arrays.stream(day().split(delimiter)).mapToInt(Integer::parseInt);
  }

  protected long[] dayNumbers() {
    return dayNumbers(DEFAULT_DELIMITER);
  }

  protected long[] dayNumbers(String delimiter) {
    return dayNumberStream(delimiter).toArray();
  }

  protected long[] dayDigits() {
    return day().chars().filter(n -> n >= '0' && n <= '9').mapToLong(n -> n - '0').toArray();
  }

  protected double[] dayDoubles() {
    return dayDoubles(DEFAULT_DELIMITER);
  }

  protected double[] dayDoubles(String delimiter) {
    return dayStream(delimiter).mapToDouble(Double::parseDouble).toArray();
  }

  protected LongStream dayNumberStream() {
    return dayNumberStream(DEFAULT_DELIMITER);
  }

  protected LongStream dayNumberStream(String delimiter) {
    return dayStream(delimiter).filter(e -> !e.isEmpty()).map(e -> e.replace("\n", "").trim()).mapToLong(Long::parseLong);
  }

  protected char[][] dayGrid() {
    return dayGrid(DEFAULT_DELIMITER);
  }

  protected char[][] dayGrid(String delimiter) {
    return dayStream(delimiter).map(String::toCharArray).toArray(char[][]::new);
  }

  public void downloadIfNotDownloaded() {
    if(!getResource(getDayPath()).exists()) {
      new FetchInput().retrieveInput(Integer.toString(day), Integer.toString(year));
    }
  }
}

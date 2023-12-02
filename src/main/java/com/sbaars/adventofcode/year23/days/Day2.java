package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day2 extends Day2023 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  public record MapGame (int num, List<MapCube> cubes) {}
  public record MapCube (String s) {}
  public record Game (int num, List<Cube> cubes) {}
  public record Cube (List<Draw> draws) {}
  public record Draw (int amount, String color) {}

  @Override
  public Object part1() {
    return input().stream()
            .filter(g -> g.cubes.stream().noneMatch(c -> c.draws.stream().anyMatch(this::invalid)))
            .mapToInt(Game::num)
            .sum();
  }

  public boolean invalid(Draw d) {
    return d.amount > max(d.color);
  }

  public int max(String color) {
    return switch (color) {
      case "red" -> 12;
      case "green" -> 13;
      case "blue" -> 14;
      default -> throw new IllegalStateException();
    };
  }

  @Override
  public Object part2() {
    return input().stream()
            .mapToLong(g -> findMaxProduct(g.cubes.stream().flatMap(c -> c.draws.stream()).toList()))
            .sum();
  }

  private List<Game> input() {
    return dayStream().map(s -> readString(s, "Game %i: %l(%s)", "; ", MapGame.class, MapCube.class))
            .map(c -> new Game(c.num, c.cubes.stream().map(s -> readString(s.s, "%l(%i %s)", ", ", Cube.class, Draw.class)).toList()))
            .toList();
  }

  private long findMaxProduct(List<Draw> ds) {
    return getProduct(ds, "red") *
            getProduct(ds, "green") *
            getProduct(ds, "blue");
  }

  private static long getProduct(List<Draw> ds, String color) {
    return ds.stream().filter(d -> d.color.equals(color)).mapToLong(d -> d.amount).max().orElse(0);
  }
}

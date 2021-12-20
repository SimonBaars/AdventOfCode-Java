package com.sbaars.adventofcode.common.grid;

import static com.sbaars.adventofcode.common.Day.DEFAULT_DELIMITER;
import static java.lang.Math.abs;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Coordinates {
  public final Set<Point> coords;

  public Coordinates(Set<Point> c) {
    this.coords = c;
  }

  public Coordinates(Stream<Point> c) {
    this.coords = c.collect(Collectors.toSet());
  }

  public Coordinates(String s){
    this(s, DEFAULT_DELIMITER, ",");
  }

  public Coordinates(String s, String delimiter, String coordDelimiter) {
    this.coords = Arrays.stream(s.split(delimiter)).map(e -> e.split(coordDelimiter)).map(e -> new Point(Integer.parseInt(e[0]), Integer.parseInt(e[1]))).collect(Collectors.toSet());
  }

  public Coordinates() {
    coords = new HashSet<>();
  }

  public Point add(Point a){
    coords.add(a);
    return a;
  }

  public Coordinates transform(UnaryOperator<Point> f){
    return new Coordinates(stream().map(f));
  }

  public Stream<Point> stream(){
    return coords.stream();
  }

  public NumGrid toGrid(){
    NumGrid g = new NumGrid(new long[sizeY()+1][sizeX()+1]);
    int minX = stream().mapToInt(e -> e.x).min().getAsInt();
    int minY = stream().mapToInt(e -> e.y).min().getAsInt();
    coords.stream().map(e -> new Point(e.y-minY, e.x-minX)).forEach(e -> g.set(e, 1L));
    return g;
  }

  private int sizeX() {
    return stream().mapToInt(e -> e.x).max().getAsInt() + abs(stream().mapToInt(e -> e.x).min().getAsInt());
  }

  private int sizeY() {
    return stream().mapToInt(e -> e.y).max().getAsInt() + abs(stream().mapToInt(e -> e.y).min().getAsInt());
  }

  public Coordinates mirrorX(){
    int sizeX = sizeX();
    return new Coordinates(coords.stream().map(p -> new Point(sizeX - p.x, p.y)));
  }

  public Coordinates mirrorY(){
    int sizeY = sizeY();
    return new Coordinates(coords.stream().map(p -> new Point(p.x, sizeY - p.y)));
  }

  public int size() {
    return coords.size();
  }
}

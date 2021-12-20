package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.common.Direction.CENTER;
import static com.sbaars.adventofcode.common.Direction.EAST;
import static com.sbaars.adventofcode.common.Direction.SOUTHEAST;
import static com.sbaars.adventofcode.common.Direction.NORTH;
import static com.sbaars.adventofcode.common.Direction.NORTHEAST;
import static com.sbaars.adventofcode.common.Direction.SOUTH;
import static com.sbaars.adventofcode.common.Direction.SOUTHWEST;
import static com.sbaars.adventofcode.common.Direction.WEST;
import static com.sbaars.adventofcode.common.Direction.NORTHWEST;

import com.sbaars.adventofcode.common.IntLoc;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.common.grid.Coordinates;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.math.BigInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day20 extends Day2021 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  @Override
  public Object part1() {
    return doDay(2);
  }

  private int doDay(int times) {
    var in = day().split("\n\n");
    char[] replace = in[0].toCharArray();
    CharGrid inputImage = new CharGrid(in[1]);
    Coordinates inputCoords = new Coordinates(IntLoc.range(inputImage.grid.length, inputImage.grid[0].length)
        .filter(e -> inputImage.grid[e.y][e.x]=='#')
        .map(IntLoc::getPoint)
        .collect(Collectors.toSet()));
    Coordinates outputImage = new Coordinates();
    for(int it = 0; it<times; it++){
      int iteration = it;
      NumGrid n = inputCoords.toGrid();
      for(int i = -1; i<=n.sizeX(); i++){
        for(int j = -1; j<=n.sizeY(); j++){
          Point thisPoint = new Point(i, j);
          String bin = Stream.of(NORTHWEST, NORTH, NORTHEAST, WEST, CENTER, EAST, SOUTHWEST, SOUTH, SOUTHEAST)
              .map(dir -> Long.toString(n.at(dir.move(thisPoint), iteration%2==1 && replace[0] == '#' ? 1 : 0)))
              .collect(Collectors.joining());
          if(replace[binToDec(bin)] == '#'){
            outputImage.add(thisPoint);
          }
        }
      }
      inputCoords = outputImage;
      outputImage = new Coordinates();
    }
    return inputCoords.size();
  }

  int binToDec(String s) {
    return Integer.parseInt(new BigInteger(s, 2).toString(10));
  }

  @Override
  public Object part2() {
    return doDay(50);
  }
}

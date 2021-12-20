package com.sbaars.adventofcode.year21.days;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static com.sbaars.adventofcode.common.Direction.EASTSOUTH;
import static com.sbaars.adventofcode.common.Direction.NORTH;
import static com.sbaars.adventofcode.common.Direction.NORTHEAST;
import static com.sbaars.adventofcode.common.Direction.SOUTH;
import static com.sbaars.adventofcode.common.Direction.SOUTHWEST;
import static com.sbaars.adventofcode.common.Direction.WEST;
import static com.sbaars.adventofcode.common.Direction.WESTNORTH;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.IntLoc;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.common.grid.Coordinates;
import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.math.BigInteger;
import java.util.stream.Collectors;

public class Day20 extends Day2021 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts(1);
//    new Day20().submitPart1();
//    new Day20().submitPart2();
  }

  @Override
  public Object part1() {
    return doDay(2);
  }

  private int doDay(int times) {
    var in = day().split("\n\n");
    char[] replace = in[0].toCharArray();
    CharGrid inputImage = new CharGrid(in[1]);
    Coordinates inputCoords = new Coordinates(IntLoc.range(inputImage.grid.length, inputImage.grid[0].length).filter(e -> inputImage.grid[e.x][e.y]=='#').map(IntLoc::getPoint).collect(Collectors.toSet()));
    Coordinates outputImage = new Coordinates();
    System.out.println(inputCoords.toGrid());
    for(int it = 0; it<times; it++){
      NumGrid n = inputCoords.toGrid();
      for(int i = -1; i<=n.sizeX()+1; i++){
        for(int j = -1; j<=n.sizeY()+1; j++){
          Point thisPoint = new Point(j, i);
          StringBuilder bin = new StringBuilder();
          Direction[] dirs = new Direction[]{WESTNORTH, NORTH, NORTHEAST, EAST, EASTSOUTH, SOUTH, SOUTHWEST, WEST};
          for(Direction dir : dirs) {
            bin.append(n.get(dir.move(thisPoint)) == -1 ? 0 : n.get(dir.move(thisPoint)));
          }
          int dec = binToDec(bin.toString());
          if(replace[dec] == '#'){
            outputImage.add(thisPoint);
          }
        }
      }
      inputCoords = outputImage;
      outputImage = new Coordinates();
      System.out.println(inputCoords.toGrid());
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

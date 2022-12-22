package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;

public class Day22 extends Day2022 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) throws IOException {
    Day22 d = new Day22();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
    System.in.read();
    d.submitPart1();
//    d.submitPart2();
  }

  public record Input () {}

  @Override
  public Object part1() {
    String[] in = day().split("\n\n");
    CharGrid grid = new CharGrid(in[0]);
    InfiniteGrid g = new InfiniteGrid(grid.grid);
    Loc start = g.grid.keySet().stream().filter(l -> grid.get(l) == '.' && l.y == 0).findFirst().get();
    Direction facing = Direction.EAST;
    String instr = in[1].trim();
    while(!instr.isEmpty()) {
      if(instr.charAt(0) == 'L' || instr.charAt(0) == 'R') {
        facing = facing.turn(instr.charAt(0) == 'R');
        instr = instr.substring(1);
      } else {
        int i;
        for(i=0; i<instr.length() && Character.isDigit(instr.charAt(i)); i++);
//        System.out.println(instr);
        int n = Integer.parseInt(instr.substring(0, i));
        instr = instr.substring(i);
        for(i = 0; i<n; i++) {
          Loc newLoc = facing.move(start);
          var atPos = g.get(newLoc);
          if(atPos.isPresent() && atPos.get() == '.') {
            start = newLoc;
          } else if(!atPos.isPresent()) {
            int j;
            Direction opposite = facing.turn().turn();
            newLoc = opposite.move(newLoc);
            for(j = 0; g.get(newLoc).isPresent(); newLoc = opposite.move(newLoc));
            newLoc = facing.move(newLoc);
            if(g.get(newLoc).get() == '.') start = newLoc;
          }
        }
      }
    }
    return (1000 * (start.y+1)) + (4 * (start.x + 1)) + facing.ordinal() - 1 + (facing == Direction.NORTH ? 4 : 0);
  }

  @Override
  public Object part2() {
    return "";
  }
}

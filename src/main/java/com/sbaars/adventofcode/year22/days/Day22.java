package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.CharGrid;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day22 extends Day2022 {
  public Day22() {
    super(22);
  }

  public static void main(String[] args) throws IOException {
    Day22 d = new Day22();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Location(int c, Direction d) {}
  public record Instruction(Location go, boolean translate, boolean swap) {}
  Map<Integer, Range> cubes = Map.of(
          5, new Range(50, 0, 99, 49)/*5*/,
          6, new Range(100, 0, 149, 49)/*6*/,
          4, new Range(50, 50, 99, 99)/*4*/,
          3, new Range(50, 100, 99, 149)/*3*/,
          2, new Range(0, 100, 49, 149)/*2*/,
          1, new Range(0, 150, 49, 199)/*1*/
  );
  Map<Location, Instruction> todo = Map.of(
          new Location(6, EAST), new Instruction(new Location(3, EAST), true, false), // A
          new Location(6, SOUTH), new Instruction(new Location(4, EAST), false, true), // B
          new Location(6, NORTH), new Instruction(new Location(1, SOUTH), false, false), // C
          new Location(4, WEST), new Instruction(new Location(2, NORTH), false, true), // D
          new Location(3, SOUTH), new Instruction(new Location(1, EAST), false, true), // E
          new Location(5, NORTH), new Instruction(new Location(1, WEST), false, true), // F
          new Location(5, WEST), new Instruction(new Location(2, WEST), true, false)  // G
  );

  @Override
  public Object part1() {
    String[] in = day().split("\n\n");
    CharGrid grid = new CharGrid(in[0]);
    InfiniteGrid g = new InfiniteGrid(grid.grid);
    Loc start = g.grid.keySet().stream().filter(l -> grid.get(l) == '.' && l.y == 0).findFirst().get();
    Direction facing = EAST;
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
            Direction opposite = facing.opposite();
            newLoc = opposite.move(newLoc);
            for(j = 0; g.get(newLoc).isPresent(); newLoc = opposite.move(newLoc));
            newLoc = facing.move(newLoc);
            if(g.get(newLoc).get() == '.') start = newLoc;
          }
        }
      }
    }
    return (1000 * (start.y+1)) + (4 * (start.x + 1)) + facing.ordinal() - 1 + (facing == NORTH ? 4 : 0);
  }

  @Override
  public Object part2() {
    var teleport = new HashMap<>(todo);
    todo.forEach((l, i) -> teleport.put(i.go, new Instruction(l, i.translate, i.swap)));
    String[] in = day().split("\n\n");
    CharGrid grid = new CharGrid(in[0]);
    InfiniteGrid g = new InfiniteGrid(grid.grid);
    Loc start = g.grid.keySet().stream().filter(l -> grid.get(l) == '.' && l.y == 0).findFirst().get();
    Direction facing = EAST;
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
            Loc finalStart = start;
            int cube = cubes.entrySet().stream().filter(e -> e.getValue().inRange(finalStart)).mapToInt(Map.Entry::getKey).findFirst().getAsInt();
            System.out.println(cube);
            var tele = teleport.get(new Location(cube, facing));
            Range oldCube = cubes.get(cube);
            Range newCube = cubes.get(tele.go.c);
            long diff =  facing.diagonal() ? start.x - oldCube.start.x : start.y - oldCube.start.y;
            long newX = tele.go.d == WEST ? newCube.start.x : tele.go.d == EAST ? newCube.end.x : tele.translate ? newCube.start.x + diff : newCube.end.x - diff;
            long newY = tele.go.d == NORTH ? newCube.start.y : tele.go.d == SOUTH ? newCube.end.y : tele.translate ? newCube.start.y + diff : newCube.end.y - diff;
            newLoc = new Loc(newX, newY);
            if(g.get(newLoc).get() == '.'){
              start = newLoc;
              facing = tele.go.d.opposite();
            }
          }
        }
      }
    }
    return (1000 * (start.y+1)) + (4 * (start.x + 1)) + facing.ordinal() - 1 + (facing == NORTH ? 4 : 0);
  }
}

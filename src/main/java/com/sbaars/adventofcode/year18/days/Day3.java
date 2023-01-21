package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.common.location.Range;
import com.sbaars.adventofcode.year18.Day2018;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day3 extends Day2018 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    List<Claim> input = input();
    Set<Loc> all = new HashSet<>();
    Set<Loc> overlap = new HashSet<>();
    for(Claim c : input){
      for(int i = 0; i<c.sizex; i++){
        for(int j = 0; j<c.sizey; j++){
          Loc loc = new Loc(c.x + i, c.y + j);
          if(!all.add(loc)){
            overlap.add(loc);
          }
        }
      }
    }
    return overlap.size();
  }

  @Override
  public Object part2() {
    List<Claim> input = input();
    return input().stream().filter(e -> input.stream().filter(f -> e.id != f.id).noneMatch(f -> f.getRange().overlaps(e.getRange()))).findFirst().get().id;
  }

  private List<Claim> input() {
    return dayStream().map(e -> readString(e, "#%n @ %n,%n: %nx%n", Claim.class)).toList();
  }

  public record Claim(long id, long x, long y, long sizex, long sizey){
    public Range getRange() {
      return new Range(x, y, x+sizex, y+sizey);
    }
  }
}

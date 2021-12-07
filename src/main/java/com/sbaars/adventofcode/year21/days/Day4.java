package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends Day2021 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  @Override
  public Object part1() {
    var in = day();
    String[] split = in.split("\n\n");
    long[] nums = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
    List<NumGrid> cards = IntStream.range(1, split.length).mapToObj(i -> split[i]).map(NumGrid::new).toList();
    for(long num : nums){
        for(NumGrid card : cards){
          if(markCard(card, num) && checkCard(card)){
            return result(card, num);
          }
        }
    }
    return "";
  }

  private long result(NumGrid card, long num) {
    return card.sumExcept(-1) * num;
  }

  private boolean markCard(NumGrid card, long num){
    return card.replace(num, -1);
  }

  private boolean checkCard(NumGrid grid){
    long[][] card = grid.grid;
    for(long[] nums : card){
      if(Arrays.stream(nums).allMatch(n -> n==-1)){
        return true;
      }
    }
    out: for(int i = 0; i<card[0].length; i++){
      for(int j = 0; j<card[i].length; j++){
        if(card[j][i] != -1){
          continue out;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public Object part2() {
    var in = day();
    String[] split = in.split("\n\n");
    long[] nums = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
    List<NumGrid> cards = IntStream.range(1, split.length).mapToObj(i -> new NumGrid(split[i])).collect(Collectors.toCollection(ArrayList::new));
    for(long num : nums){
      for(int i = 0; i<cards.size(); i++){
        NumGrid card = cards.get(i);
        if(markCard(card, num) && checkCard(card)){
          if(cards.size() == 1){
            return result(card, num);
          } else {
            cards.remove(i);
            i--;
          }
        }
      }
    }
    return "";
  }
}

package com.sbaars.adventofcode.year21.days;

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
//    new Day4().submitPart1();
//    new Day4().submitPart2();
  }

  @Override
  public Object part1() {
    var in = day();
    String[] split = in.split("\n\n");
    long[] nums = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
    List<long[][]> cards = IntStream.range(1, split.length).mapToObj(i -> numGrid(split[i])).toList();
    for(long num : nums){
        for(long[][] card : cards){
          if(markCard(card, num) && checkCard(card)){
            return result(card, num);
          }
        }
    }
    return "";
  }

  private long result(long[][] card, long num) {
    long res = 0;
    for(int i = 0; i< card.length; i++){
      for(int j = 0; j< card[i].length; j++){
        if(card[i][j] != -1){
          res += card[i][j];
        }
      }
    }
    return res * num;
  }

  private boolean markCard(long[][] card, long num){
    for(int i = 0; i< card.length; i++){
      for(int j = 0; j< card[i].length; j++){
        if(card[i][j] == num){
          card[i][j] = -1;
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkCard(long[][] card){
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

  private long[][] numGrid(String str) {
    String[] lines = str.split("\n");
    long[][] res = new long[lines.length][];
    for(int i = 0; i<lines.length; i++){
      res[i] = Arrays.stream(lines[i].split(" ")).map(String::trim).filter(e -> !e.isEmpty()).mapToLong(Long::parseLong).toArray();
    }
    return res;
  }

  @Override
  public Object part2() {
    var in = day();
    String[] split = in.split("\n\n");
    long[] nums = Arrays.stream(split[0].split(",")).mapToLong(Long::parseLong).toArray();
    List<long[][]> cards = IntStream.range(1, split.length).mapToObj(i -> numGrid(split[i])).collect(Collectors.toCollection(ArrayList::new));
    for(long num : nums){
      for(int i = 0; i<cards.size(); i++){
        long[][] card = cards.get(i);
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

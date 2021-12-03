package com.sbaars.adventofcode.year21.days;

import static java.lang.Integer.parseInt;

import com.sbaars.adventofcode.year21.Day2021;
import java.util.ArrayList;
import java.util.List;

public class Day3 extends Day2021 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
//    new Day3().submitPart1();
//    new Day3().submitPart2();
  }

  @Override
  public Object part1() {
    var in = dayStrings();
    String most = "";
    String least = "";

    for(int i = 0; i<in[0].length(); i++){
      if(moreZeros(in, i)){
        most+="0";
        least+="1";
      } else {
        most+="1";
        least+="0";
      }
    }
    return parseInt(most, 2) * parseInt(least, 2);
  }

  private boolean moreZeros(String[] in, int i) {
    int ones = 0, zeros = 0;
    for(String s : in){
      if(s.charAt(i) == '1'){
        ones++;
      } else {
        zeros++;
      }
    }
    return ones<zeros;
  }

  @Override
  public Object part2() {
    var in = dayStream().toList();
    return parseInt(findVal(in, true, 0).get(0), 2) * parseInt(findVal(in, false, 0).get(0), 2);
  }

  private List<String> findVal(List<String> in, boolean high, int pos) {
    List<String> res = new ArrayList<>(in);
    res.removeIf(e -> e.charAt(pos) == (!moreZeros(in.toArray(String[]::new), pos)^high ? '1' : '0'));
    if(res.size() == 1) return res;
    return findVal(res, high, pos+1);
  }
}

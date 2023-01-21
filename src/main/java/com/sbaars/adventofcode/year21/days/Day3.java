package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.year21.Day2021;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Day3 extends Day2021 implements HasRecursion {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    var in = dayStrings();
    StringBuilder most = new StringBuilder();
    StringBuilder least = new StringBuilder();

    for (int i = 0; i < in[0].length(); i++) {
      if (moreZeros(in, i)) {
        most.append("0");
        least.append("1");
      } else {
        most.append("1");
        least.append("0");
      }
    }
    return parseInt(most.toString(), 2) * parseInt(least.toString(), 2);
  }

  private boolean moreZeros(String[] in, int i) {
    int ones = 0, zeros = 0;
    for (String s : in) {
      if (s.charAt(i) == '1') {
        ones++;
      } else {
        zeros++;
      }
    }
    return ones < zeros;
  }

  @Override
  public Object part2() {
    var in = dayStream().toList();
    return parseInt(findVal(in, true, 0).get(0), 2) * parseInt(findVal(in, false, 0).get(0), 2);
  }

  private List<String> findVal(List<String> in, boolean high, int pos) {
    List<String> res = new ArrayList<>(in);
    res.removeIf(e -> e.charAt(pos) == (!moreZeros(in.toArray(String[]::new), pos) ^ high ? '1' : '0'));
    if (res.size() == 1) return res;
    return findVal(res, high, pos + 1);
  }
}

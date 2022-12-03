package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.List;

public class Day3 extends Day2022 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) throws IOException {
    Day d = new Day3();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
    System.in.read();
//    d.submitPart1();
    d.submitPart2();
  }

  @Override
  public Object part1() {
    return dayStream().map(String::trim).map(e -> new String[]{e.substring(0, e.length()/2), e.substring(e.length()/2)})
            .mapToLong(e -> {
//              System.out.println(e[0]+", "+e[1]);
              List<Integer> chars1 = e[0].chars().mapToObj(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26).toList();
              int x =  e[1].chars().map(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26).filter(i -> chars1.contains(i)).findFirst().getAsInt();
//              System.out.println(x);
              return x;
    }).sum()  ;

  }

  @Override
  public Object part2() {
    List<String> s = dayStream().map(String::trim).toList();
    long sum = 0;
    for(int x = 0;x <s.size();x+=3) {
      String a = s.get(x);
      String b = s.get(x+1);
      String c = s.get(x+2);
      List<Integer> chars1 = a.chars().mapToObj(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26).toList();
      List<Integer> chars2 = b.chars().mapToObj(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26).toList();
      int  y =  c.chars().map(i -> i >= 'a' && i <= 'z' ? i - 'a' + 1 : i - 'A' + 1 + 26).filter(i -> chars1.contains(i) && chars2.contains(i)).findFirst().getAsInt();
    sum += y;
    }
    return sum;
  }
}

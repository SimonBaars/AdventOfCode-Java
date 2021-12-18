package com.sbaars.adventofcode.year21.days;

import static java.lang.Integer.parseInt;

import com.sbaars.adventofcode.year21.Day2021;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day18 extends Day2021 {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts(8);
//    new Day18().submitPart1();
//    new Day18().submitPart2();
  }

  @Override
  public Object part1() {
    var in = dayStream().filter(e -> !e.isEmpty()).map(this::reduce).collect(Collectors.toCollection(ArrayList::new));
    while(in.size()>1){
      String one = in.remove(0);
      String two = in.remove(0);
      in.add(0, reduce("["+one+","+two+"]"));
    }
    return magnitude(in.get(0));
  }

  private String reduce(String s) {
    String prev = "";
    while(!prev.equals(s)) {
      prev = s;
      s = explodeAll(s);
    }
    return s;
  }

  private String explodeAll(String s) {
    int depth = 0;
    for(int i = 0; i<s.length()-5; i++){
      if(s.charAt(i) == '[') depth++;
      else if(s.charAt(i) == ']') depth--;
      if(depth>=4) {
        String pair = s.substring(i, i + 5);
        if (pair.replaceFirst("\\[[0-9],[0-9]\\]", "").isEmpty()) {
          int leftNum = 0;
          int rightNum = 0;
          int leftIndex = -1;
          int rightIndex = -1;
          for(int j = i-1; j>=0; j--){
            char c = s.charAt(j);
            if(c >= '0' && c <= '9'){
              leftNum = c - '0';
              leftIndex = j;
              break;
            }
          }
          for(int k = i+5; k<s.length(); k++){
            char c = s.charAt(k);
            if(c >= '0' && c <= '9'){
              rightNum = c - '0';
              rightIndex = k;
              break;
            }
          }
          leftNum+=s.charAt(i+1)-'0';
          rightNum+=s.charAt(i+3)-'0';
          String leftPair = Integer.toString(leftNum);
          if(leftNum>=10) {
            leftPair = "["+(leftNum/2)+","+(leftNum%2==0 ? leftNum/2 : (leftNum/2)+1)+"]";
          }
          String rightPair = Integer.toString(rightNum);
          if(rightNum>=10) {
            rightPair = "["+(rightNum/2)+","+(rightNum%2==0 ? rightNum/2 : (rightNum/2)+1)+"]";
          }
          String leftPart = leftIndex == -1 ? s.substring(0, i) : s.substring(0, leftIndex) + leftPair + s.substring(leftIndex+1, i);
          String rightPart = rightIndex == -1 ? s.substring(i+5) : s.substring(i+5, rightIndex) + rightPair + s.substring(rightIndex+1);
          return leftPart + 0 + rightPart;
        }
      }
    }
    return s;
  }

  private int magnitude(String s) {
    String prev = "";
    while(!prev.equals(s)) {
      prev = s;
      s = calcMag(s);
    }
    return Integer.parseInt(s);
  }

  private String calcMag(String s) {
    for(int i = 0; i<s.length()-5; i++){
        String pair = s.substring(i);
        if (pair.endsWith(pair.replaceFirst("\\[[0-9]*,[0-9]*\\]", ""))) {
          int leftNum = 0;
          int rightNum = 0;
          int leftIndex = -1;
          int rightIndex = -1;
          for(int j = i+1; j<s.length(); j++){
            char c = s.charAt(j);
            if(c < '0' || c > '9'){
              leftNum = parseInt(s.substring(i+1, j));
              leftIndex = j;
              break;
            }
          }
          for(int k = leftIndex+1; k<s.length(); k++){
            char c = s.charAt(k);
            if(c < '0' || c > '9'){
              rightNum = parseInt(s.substring(leftIndex+1, k));
              rightIndex = k;
              break;
            }
          }
          return s.substring(0, i) + ((leftNum*3)+(rightNum*2)) + s.substring(rightIndex+1);
        }
      }
    return s;
  }

  int charToInt(char c){
    int res = c - '0';
    if(res>=0 && res<=9) return res;
    return -1;
  }

  @Override
  public Object part2() {
    var in = dayStream().filter(e -> !e.isEmpty()).map(this::reduce).collect(Collectors.toCollection(ArrayList::new));
    return IntStream.range(0, in.size()).boxed().flatMap(x -> IntStream.range(0, in.size()).filter(y -> x!=y).mapToObj(y -> new Point(x, y))).mapToInt(p -> magnitude(reduce("["+in.get(p.x)+","+in.get(p.y)+"]"))).max().getAsInt();
  }

  public record Pair (Optional<Pair> p1, Optional<Pair> p2, Optional<Integer> num1, Optional<Integer> num2, Integer depth) { }
}

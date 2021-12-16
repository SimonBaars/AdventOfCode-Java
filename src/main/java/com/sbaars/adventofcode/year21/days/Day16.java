package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.year21.Day2021;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day16 extends Day2021 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
//    new Day16().submitPart1();
//    new Day16().submitPart2();
  }



  @Override
  public Object part1() {
//    System.out.println(findSum("D2FE28"));
//    System.out.println(findSum("38006F45291200"));
//    System.out.println(findSum("EE00D40C823060"));
//    System.out.println(findSum("8A004A801A8002F478"));
    return findSum(day());
  }

  private long findSum(String hex) {
    var in = hexToBin(hex.trim());
    return findPackets(in);
  }

  private long findPackets(String in) {
    long sum = 0;
    for(int i = 0; i< in.length();){
      if(in.substring(i).chars().allMatch(e -> e == '0')) break;
      int version = binToDec(in.substring(i, i+3));
      sum+=version;
      int id = binToDec(in.substring(i+3, i+6));
      i+=6;
      if(id == 4){
        for(;;i+=5){
          if(in.charAt(i) == '0'){
            i+=5;
            break;
          }
        }
      } else {
        int lengthLength = 15;
        boolean b = in.charAt(i) == '1';
        if(b){
          lengthLength = 11;
        }
        i++;
        int length = binToDec(in.substring(i, i+lengthLength));
        i+=lengthLength;
        if(!b) {
          sum += findPackets(in.substring(i, i + length));
          i += length;
        }
      }
    }
    return sum;
  }

  String hexToBin(String s) {
    return String.format("%"+(s.length()*4)+"s", new BigInteger(s, 16).toString(2)).replace(" ", "0");
  }

  int binToDec(String s) {
    return Integer.parseInt(new BigInteger(s, 2).toString(10));
  }

  @Override
  public Object part2() {
    return findSum2(day());
  }

  private long findSum2(String hex) {
    var in = hexToBin(hex.trim());
    return findPackets2(in, Integer.MAX_VALUE).get(0);
  }

  private List<Long> findPackets2(String in, int toParse) {
    List<Long> res = new ArrayList<>();
    long sum = 0;
    for(int i = 0, parsed = 0; i< in.length();parsed++){
      if(parsed >= toParse) break;
      if(in.substring(i).chars().allMatch(e -> e == '0')) break;
      int version = binToDec(in.substring(i, i+3));
      int id = binToDec(in.substring(i+3, i+6));
      i+=6;
      if(id == 4){
        String s = "";
        for(;;i+=5){
          s+=in.substring(i+1, i+5);
          if(in.charAt(i) == '0'){
            i+=5;
            break;
          }
        }
        res.add((long)binToDec(s));
      } else {
        int lengthLength = 15;
        boolean b = in.charAt(i) == '1';
        if(b){
          lengthLength = 11;
        }
        i++;
        int length = binToDec(in.substring(i, i+lengthLength));
        i+=lengthLength;
        List<Long> op = findPackets2(in.substring(i, b ? in.length() : (i + length)), b ? length : Integer.MAX_VALUE);
        performOp(op, id);
        i += length;
      }
    }
    return res;
  }

  private void performOp(List<Long> op, int id) {
    
  }
}

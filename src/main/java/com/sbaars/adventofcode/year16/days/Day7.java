package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Day2016 {
  public Day7() {
    super(7);
  }

  public static void main(String[] args) {
    new Day7().printParts();
  }

  private record IPv7(List<String> supernets, List<String> hypernets) {
    private static final Pattern PATTERN = Pattern.compile("\\[([^\\]]+)\\]");

    public static IPv7 parse(String ip) {
      List<String> hypernets = new ArrayList<>();
      Matcher matcher = PATTERN.matcher(ip);
      while (matcher.find()) {
        hypernets.add(matcher.group(1));
      }
      List<String> supernets = new ArrayList<>(Arrays.asList(PATTERN.split(ip)));
      return new IPv7(supernets, hypernets);
    }

    public boolean supportsTLS() {
      return supernets.stream().anyMatch(IPv7::hasABBA) && 
             hypernets.stream().noneMatch(IPv7::hasABBA);
    }

    public boolean supportsSSL() {
      Set<String> abas = new HashSet<>();
      for (String supernet : supernets) {
        for (int i = 0; i < supernet.length() - 2; i++) {
          if (isABA(supernet, i)) {
            abas.add(supernet.substring(i, i + 3));
          }
        }
      }

      for (String aba : abas) {
        String bab = "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
        if (hypernets.stream().anyMatch(h -> h.contains(bab))) {
          return true;
        }
      }
      return false;
    }

    private static boolean hasABBA(String s) {
      for (int i = 0; i < s.length() - 3; i++) {
        if (isABBA(s, i)) {
          return true;
        }
      }
      return false;
    }

    private static boolean isABBA(String s, int i) {
      return s.charAt(i) == s.charAt(i + 3) && 
             s.charAt(i + 1) == s.charAt(i + 2) && 
             s.charAt(i) != s.charAt(i + 1);
    }

    private static boolean isABA(String s, int i) {
      return s.charAt(i) == s.charAt(i + 2) && 
             s.charAt(i) != s.charAt(i + 1);
    }
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(IPv7::parse)
        .filter(IPv7::supportsTLS)
        .count();
  }

  @Override
  public Object part2() {
    return dayStream()
        .map(IPv7::parse)
        .filter(IPv7::supportsSSL)
        .count();
  }
}

package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends Day2017 {
  private static class Component {
    final int port1, port2;
    
    Component(int port1, int port2) {
      this.port1 = port1;
      this.port2 = port2;
    }
    
    int getOtherPort(int port) {
      return port == port1 ? port2 : port1;
    }
    
    int getStrength() {
      return port1 + port2;
    }
  }
  
  private final List<Component> components = new ArrayList<>();
  
  public Day24() {
    super(24);
    for (String line : dayStream().toList()) {
      String[] ports = line.split("/");
      components.add(new Component(Integer.parseInt(ports[0]), Integer.parseInt(ports[1])));
    }
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  private int findStrongestBridge(int currentPort, Set<Component> used) {
    int maxStrength = 0;
    
    for (Component c : components) {
      if (!used.contains(c) && (c.port1 == currentPort || c.port2 == currentPort)) {
        used.add(c);
        int strength = c.getStrength() + findStrongestBridge(c.getOtherPort(currentPort), used);
        maxStrength = Math.max(maxStrength, strength);
        used.remove(c);
      }
    }
    
    return maxStrength;
  }

  @Override
  public Object part1() {
    return findStrongestBridge(0, new HashSet<>());
  }

  @Override
  public Object part2() {
    return 0;
  }
}

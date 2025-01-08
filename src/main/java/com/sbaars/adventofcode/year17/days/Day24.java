package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;
import java.util.stream.Collectors;

public class Day24 extends Day2017 {
  private record Component(int port1, int port2) {
    boolean hasPort(int port) {
      return port1 == port || port2 == port;
    }
    
    int getOtherPort(int port) {
      return port == port1 ? port2 : port1;
    }
    
    int strength() {
      return port1 + port2;
    }
  }
  
  private record Bridge(int length, int strength) {
    Bridge addComponent(Component c) {
      return new Bridge(length + 1, strength + c.strength());
    }
  }

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  private List<Component> parseComponents() {
    return dayStream().map(line -> {
      String[] parts = line.split("/");
      return new Component(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }).collect(Collectors.toList());
  }

  private int findStrongestBridge(List<Component> availableComponents, int currentPort, int currentStrength) {
    int maxStrength = currentStrength;
    
    for (int i = 0; i < availableComponents.size(); i++) {
      Component comp = availableComponents.get(i);
      if (comp.hasPort(currentPort)) {
        List<Component> remaining = new ArrayList<>(availableComponents);
        remaining.remove(i);
        int nextPort = comp.getOtherPort(currentPort);
        int strength = findStrongestBridge(remaining, nextPort, currentStrength + comp.strength());
        maxStrength = Math.max(maxStrength, strength);
      }
    }
    
    return maxStrength;
  }
  
  private Bridge findLongestStrongestBridge(List<Component> availableComponents, int currentPort, Bridge currentBridge) {
    Bridge bestBridge = currentBridge;
    
    for (int i = 0; i < availableComponents.size(); i++) {
      Component comp = availableComponents.get(i);
      if (comp.hasPort(currentPort)) {
        List<Component> remaining = new ArrayList<>(availableComponents);
        remaining.remove(i);
        int nextPort = comp.getOtherPort(currentPort);
        Bridge newBridge = findLongestStrongestBridge(remaining, nextPort, currentBridge.addComponent(comp));
        
        if (newBridge.length > bestBridge.length || 
            (newBridge.length == bestBridge.length && newBridge.strength > bestBridge.strength)) {
          bestBridge = newBridge;
        }
      }
    }
    
    return bestBridge;
  }

  @Override
  public Object part1() {
    List<Component> components = parseComponents();
    return findStrongestBridge(components, 0, 0);
  }

  @Override
  public Object part2() {
    List<Component> components = parseComponents();
    return findLongestStrongestBridge(components, 0, new Bridge(0, 0)).strength;
  }
}

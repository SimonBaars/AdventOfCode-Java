package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Day24 extends Day2018 {
  private static class Group implements Cloneable {
    int units;
    final int hitPoints;
    int attackDamage;
    final String attackType;
    final int initiative;
    final Set<String> weaknesses = new HashSet<>();
    final Set<String> immunities = new HashSet<>();
    final String army;

    public Group(int units, int hitPoints, int attackDamage, String attackType, int initiative, 
                Set<String> weaknesses, Set<String> immunities, String army) {
      this.units = units;
      this.hitPoints = hitPoints;
      this.attackDamage = attackDamage;
      this.attackType = attackType;
      this.initiative = initiative;
      this.weaknesses.addAll(weaknesses);
      this.immunities.addAll(immunities);
      this.army = army;
    }

    public int getEffectivePower() {
      return units * attackDamage;
    }

    public int calculateDamage(Group defender) {
      if (defender.immunities.contains(attackType)) return 0;
      int damage = getEffectivePower();
      if (defender.weaknesses.contains(attackType)) damage *= 2;
      return damage;
    }

    @Override
    public Group clone() {
      return new Group(units, hitPoints, attackDamage, attackType, initiative, 
                      new HashSet<>(weaknesses), new HashSet<>(immunities), army);
    }
  }

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  private List<Group> parseInput(String input) {
    List<Group> groups = new ArrayList<>();
    String[] armies = input.split("\n\n");
    
    for (String army : armies) {
      String[] lines = army.split("\n");
      String armyName = lines[0].contains("Immune") ? "Immune System" : "Infection";
      
      for (int i = 1; i < lines.length; i++) {
        if (lines[i].trim().isEmpty()) continue;
        
        Pattern p = Pattern.compile("(\\d+) units each with (\\d+) hit points (\\([^)]*\\) )?with an attack that does (\\d+) (\\w+) damage at initiative (\\d+)");
        Matcher m = p.matcher(lines[i]);
        
        if (m.find()) {
          int units = Integer.parseInt(m.group(1));
          int hitPoints = Integer.parseInt(m.group(2));
          String modifiers = m.group(3);
          int attackDamage = Integer.parseInt(m.group(4));
          String attackType = m.group(5);
          int initiative = Integer.parseInt(m.group(6));
          
          Set<String> weaknesses = new HashSet<>();
          Set<String> immunities = new HashSet<>();
          
          if (modifiers != null) {
            String mod = modifiers.substring(1, modifiers.length() - 2);
            for (String part : mod.split("; ")) {
              if (part.startsWith("weak to ")) {
                weaknesses.addAll(Arrays.asList(part.substring(8).split(", ")));
              } else if (part.startsWith("immune to ")) {
                immunities.addAll(Arrays.asList(part.substring(10).split(", ")));
              }
            }
          }
          
          groups.add(new Group(units, hitPoints, attackDamage, attackType, initiative, weaknesses, immunities, armyName));
        }
      }
    }
    return groups;
  }

  private Map<Group, Group> selectTargets(List<Group> attackers, List<Group> defenders) {
    Map<Group, Group> targets = new HashMap<>();
    Set<Group> selectedTargets = new HashSet<>();
    
    List<Group> sortedAttackers = new ArrayList<>(attackers);
    sortedAttackers.sort((a, b) -> {
      int powerCompare = Integer.compare(b.getEffectivePower(), a.getEffectivePower());
      return powerCompare != 0 ? powerCompare : Integer.compare(b.initiative, a.initiative);
    });
    
    for (Group attacker : sortedAttackers) {
      int maxDamage = 0;
      Group selectedTarget = null;
      
      for (Group defender : defenders) {
        if (selectedTargets.contains(defender) || defender.units <= 0) continue;
        
        int damage = attacker.calculateDamage(defender);
        if (damage > maxDamage) {
          maxDamage = damage;
          selectedTarget = defender;
        } else if (damage == maxDamage && damage > 0 && selectedTarget != null) {
          if (defender.getEffectivePower() > selectedTarget.getEffectivePower() ||
              (defender.getEffectivePower() == selectedTarget.getEffectivePower() && 
               defender.initiative > selectedTarget.initiative)) {
            selectedTarget = defender;
          }
        }
      }
      
      if (selectedTarget != null && maxDamage > 0) {
        targets.put(attacker, selectedTarget);
        selectedTargets.add(selectedTarget);
      }
    }
    
    return targets;
  }

  private boolean fight(List<Group> groups) {
    List<Group> immuneSystem = groups.stream()
        .filter(g -> g.army.equals("Immune System") && g.units > 0)
        .collect(Collectors.toList());
    List<Group> infection = groups.stream()
        .filter(g -> g.army.equals("Infection") && g.units > 0)
        .collect(Collectors.toList());
    
    Map<Group, Group> immuneTargets = selectTargets(immuneSystem, infection);
    Map<Group, Group> infectionTargets = selectTargets(infection, immuneSystem);
    
    boolean anyKilled = false;
    
    List<Group> allGroups = new ArrayList<>(groups);
    allGroups.sort((a, b) -> Integer.compare(b.initiative, a.initiative));
    
    for (Group attacker : allGroups) {
      if (attacker.units <= 0) continue;
      
      Group target = attacker.army.equals("Immune System") ? 
          immuneTargets.get(attacker) : infectionTargets.get(attacker);
      
      if (target != null) {
        int damage = attacker.calculateDamage(target);
        int unitsKilled = Math.min(damage / target.hitPoints, target.units);
        target.units -= unitsKilled;
        if (unitsKilled > 0) anyKilled = true;
      }
    }
    
    return anyKilled;
  }

  private List<Group> cloneGroups(List<Group> groups) {
    return groups.stream().map(Group::clone).collect(Collectors.toList());
  }

  private void applyBoost(List<Group> groups, int boost) {
    for (Group group : groups) {
      if (group.army.equals("Immune System")) {
        group.attackDamage += boost;
      }
    }
  }

  private BattleResult simulateBattle(List<Group> groups) {
    while (true) {
      long immuneCount = groups.stream()
          .filter(g -> g.army.equals("Immune System") && g.units > 0)
          .count();
      long infectionCount = groups.stream()
          .filter(g -> g.army.equals("Infection") && g.units > 0)
          .count();
      
      if (immuneCount == 0) return new BattleResult(false, 0);
      if (infectionCount == 0) return new BattleResult(true, groups.stream()
          .filter(g -> g.units > 0)
          .mapToInt(g -> g.units)
          .sum());
      if (!fight(groups)) return new BattleResult(false, 0); // Stalemate
    }
  }

  private static class BattleResult {
    final boolean immuneSystemWon;
    final int remainingUnits;

    BattleResult(boolean immuneSystemWon, int remainingUnits) {
      this.immuneSystemWon = immuneSystemWon;
      this.remainingUnits = remainingUnits;
    }
  }

  @Override
  public Object part1() {
    List<Group> groups = parseInput(day());
    
    while (true) {
      long immuneCount = groups.stream()
          .filter(g -> g.army.equals("Immune System") && g.units > 0)
          .count();
      long infectionCount = groups.stream()
          .filter(g -> g.army.equals("Infection") && g.units > 0)
          .count();
      
      if (immuneCount == 0 || infectionCount == 0) break;
      if (!fight(groups)) break;
    }
    
    return groups.stream()
        .filter(g -> g.units > 0)
        .mapToInt(g -> g.units)
        .sum();
  }

  @Override
  public Object part2() {
    List<Group> originalGroups = parseInput(day());
    int low = 0;
    int high = 10000; // Start with a reasonable upper bound
    int result = -1;
    
    while (low <= high) {
      int mid = (low + high) / 2;
      List<Group> groups = cloneGroups(originalGroups);
      applyBoost(groups, mid);
      BattleResult battleResult = simulateBattle(groups);
      
      if (battleResult.immuneSystemWon) {
        result = battleResult.remainingUnits;
        high = mid - 1;
      } else {
        low = mid + 1;
      }
    }
    
    return result;
  }
}

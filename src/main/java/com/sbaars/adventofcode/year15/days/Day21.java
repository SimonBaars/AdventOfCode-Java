package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day2015 {
    private static final int PLAYER_HP = 100;
    private static final List<Item> WEAPONS = Arrays.asList(
        new Item("Dagger", 8, 4, 0),
        new Item("Shortsword", 10, 5, 0),
        new Item("Warhammer", 25, 6, 0),
        new Item("Longsword", 40, 7, 0),
        new Item("Greataxe", 74, 8, 0)
    );
    
    private static final List<Item> ARMOR = Arrays.asList(
        new Item("Leather", 13, 0, 1),
        new Item("Chainmail", 31, 0, 2),
        new Item("Splintmail", 53, 0, 3),
        new Item("Bandedmail", 75, 0, 4),
        new Item("Platemail", 102, 0, 5)
    );
    
    private static final List<Item> RINGS = Arrays.asList(
        new Item("Damage +1", 25, 1, 0),
        new Item("Damage +2", 50, 2, 0),
        new Item("Damage +3", 100, 3, 0),
        new Item("Defense +1", 20, 0, 1),
        new Item("Defense +2", 40, 0, 2),
        new Item("Defense +3", 80, 0, 3)
    );

    private record Item(String name, int cost, int damage, int armor) {}
    private record Character(int hp, int damage, int armor) {}
    private record Equipment(Item weapon, Item armor, Item ring1, Item ring2) {
        public int getTotalCost() {
            return weapon.cost() + 
                   (armor != null ? armor.cost() : 0) + 
                   (ring1 != null ? ring1.cost() : 0) + 
                   (ring2 != null ? ring2.cost() : 0);
        }
        
        public int getTotalDamage() {
            return weapon.damage() + 
                   (armor != null ? armor.damage() : 0) + 
                   (ring1 != null ? ring1.damage() : 0) + 
                   (ring2 != null ? ring2.damage() : 0);
        }
        
        public int getTotalArmor() {
            return (armor != null ? armor.armor() : 0) + 
                   (ring1 != null ? ring1.armor() : 0) + 
                   (ring2 != null ? ring2.armor() : 0);
        }
    }

    public Day21() {
        super(21);
    }

    public static void main(String[] args) {
        Day21 day = new Day21();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 21, 2);
    }

    @Override
    public Object part1() {
        Character boss = parseBoss();
        return findMinGoldToWin(boss);
    }

    @Override
    public Object part2() {
        Character boss = parseBoss();
        return findMaxGoldToLose(boss);
    }

    private Character parseBoss() {
        List<String> lines = dayStream().collect(Collectors.toList());
        int hp = Integer.parseInt(lines.get(0).split(": ")[1]);
        int damage = Integer.parseInt(lines.get(1).split(": ")[1]);
        int armor = Integer.parseInt(lines.get(2).split(": ")[1]);
        return new Character(hp, damage, armor);
    }

    private int findMinGoldToWin(Character boss) {
        int minGold = Integer.MAX_VALUE;
        
        // Try all possible equipment combinations
        for (Item weapon : WEAPONS) {
            // Try with no armor and no rings
            minGold = Math.min(minGold, tryEquipment(weapon, null, null, null, boss));
            
            // Try with armor but no rings
            for (Item armor : ARMOR) {
                minGold = Math.min(minGold, tryEquipment(weapon, armor, null, null, boss));
            }
            
            // Try with one ring, no armor
            for (Item ring1 : RINGS) {
                minGold = Math.min(minGold, tryEquipment(weapon, null, ring1, null, boss));
            }
            
            // Try with two rings, no armor
            for (int i = 0; i < RINGS.size(); i++) {
                for (int j = i + 1; j < RINGS.size(); j++) {
                    minGold = Math.min(minGold, tryEquipment(weapon, null, RINGS.get(i), RINGS.get(j), boss));
                }
            }
            
            // Try with armor and one ring
            for (Item armor : ARMOR) {
                for (Item ring1 : RINGS) {
                    minGold = Math.min(minGold, tryEquipment(weapon, armor, ring1, null, boss));
                }
            }
            
            // Try with armor and two rings
            for (Item armor : ARMOR) {
                for (int i = 0; i < RINGS.size(); i++) {
                    for (int j = i + 1; j < RINGS.size(); j++) {
                        minGold = Math.min(minGold, tryEquipment(weapon, armor, RINGS.get(i), RINGS.get(j), boss));
                    }
                }
            }
        }
        
        return minGold;
    }

    private int tryEquipment(Item weapon, Item armor, Item ring1, Item ring2, Character boss) {
        Equipment equipment = new Equipment(weapon, armor, ring1, ring2);
        Character player = new Character(PLAYER_HP, equipment.getTotalDamage(), equipment.getTotalArmor());
        
        return simulateFight(player, boss) ? equipment.getTotalCost() : Integer.MAX_VALUE;
    }

    private boolean simulateFight(Character player, Character boss) {
        int playerDamagePerTurn = Math.max(1, player.damage() - boss.armor());
        int bossDamagePerTurn = Math.max(1, boss.damage() - player.armor());
        
        // Player goes first, so they need to kill the boss in equal or fewer turns
        int turnsToKillBoss = (boss.hp() + playerDamagePerTurn - 1) / playerDamagePerTurn;
        int turnsToKillPlayer = (player.hp() + bossDamagePerTurn - 1) / bossDamagePerTurn;
        
        return turnsToKillBoss <= turnsToKillPlayer;
    }

    private int findMaxGoldToLose(Character boss) {
        int maxGold = 0;
        
        // Try all possible equipment combinations
        for (Item weapon : WEAPONS) {
            // Try with no armor and no rings
            maxGold = Math.max(maxGold, tryEquipmentToLose(weapon, null, null, null, boss));
            
            // Try with armor but no rings
            for (Item armor : ARMOR) {
                maxGold = Math.max(maxGold, tryEquipmentToLose(weapon, armor, null, null, boss));
            }
            
            // Try with one ring, no armor
            for (Item ring1 : RINGS) {
                maxGold = Math.max(maxGold, tryEquipmentToLose(weapon, null, ring1, null, boss));
            }
            
            // Try with two rings, no armor
            for (int i = 0; i < RINGS.size(); i++) {
                for (int j = i + 1; j < RINGS.size(); j++) {
                    maxGold = Math.max(maxGold, tryEquipmentToLose(weapon, null, RINGS.get(i), RINGS.get(j), boss));
                }
            }
            
            // Try with armor and one ring
            for (Item armor : ARMOR) {
                for (Item ring1 : RINGS) {
                    maxGold = Math.max(maxGold, tryEquipmentToLose(weapon, armor, ring1, null, boss));
                }
            }
            
            // Try with armor and two rings
            for (Item armor : ARMOR) {
                for (int i = 0; i < RINGS.size(); i++) {
                    for (int j = i + 1; j < RINGS.size(); j++) {
                        maxGold = Math.max(maxGold, tryEquipmentToLose(weapon, armor, RINGS.get(i), RINGS.get(j), boss));
                    }
                }
            }
        }
        
        return maxGold;
    }

    private int tryEquipmentToLose(Item weapon, Item armor, Item ring1, Item ring2, Character boss) {
        Equipment equipment = new Equipment(weapon, armor, ring1, ring2);
        Character player = new Character(PLAYER_HP, equipment.getTotalDamage(), equipment.getTotalArmor());
        
        return simulateFight(player, boss) ? 0 : equipment.getTotalCost();
    }
}

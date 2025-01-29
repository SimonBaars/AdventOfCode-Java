package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Day2015 {
    private static final int PLAYER_HP = 50;
    private static final int PLAYER_MANA = 500;
    
    private enum Spell {
        MAGIC_MISSILE(53, 0, 4, 0, 0, 0),
        DRAIN(73, 0, 2, 2, 0, 0),
        SHIELD(113, 6, 0, 0, 7, 0),
        POISON(173, 6, 0, 0, 0, 3),
        RECHARGE(229, 5, 0, 0, 0, 0);

        final int cost;
        final int duration;
        final int damage;
        final int heal;
        final int armor;
        final int manaRegen;

        Spell(int cost, int duration, int damage, int heal, int armor, int manaRegen) {
            this.cost = cost;
            this.duration = duration;
            this.damage = damage;
            this.heal = heal;
            this.armor = armor;
            this.manaRegen = manaRegen;
        }
    }

    private record GameState(int playerHp, int playerMana, int bossHp, Map<Spell, Integer> activeEffects) {
        public GameState applyEffects() {
            int newPlayerHp = playerHp;
            int newPlayerMana = playerMana;
            int newBossHp = bossHp;
            
            Map<Spell, Integer> newEffects = new HashMap<>();
            for (var entry : activeEffects.entrySet()) {
                Spell spell = entry.getKey();
                int duration = entry.getValue();
                
                if (duration > 0) {
                    if (spell == Spell.POISON) {
                        newBossHp -= 3;
                    } else if (spell == Spell.RECHARGE) {
                        newPlayerMana += 101;
                    }
                    
                    if (duration > 1) {
                        newEffects.put(spell, duration - 1);
                    }
                }
            }
            
            return new GameState(newPlayerHp, newPlayerMana, newBossHp, newEffects);
        }
        
        public boolean canCastSpell(Spell spell) {
            return playerMana >= spell.cost && !activeEffects.containsKey(spell);
        }
        
        public GameState castSpell(Spell spell) {
            Map<Spell, Integer> newEffects = new HashMap<>(activeEffects);
            int newPlayerHp = playerHp;
            int newPlayerMana = playerMana - spell.cost;
            int newBossHp = bossHp;
            
            if (spell.duration > 0) {
                newEffects.put(spell, spell.duration);
            } else {
                if (spell == Spell.MAGIC_MISSILE) {
                    newBossHp -= 4;
                } else if (spell == Spell.DRAIN) {
                    newBossHp -= 2;
                    newPlayerHp += 2;
                }
            }
            
            return new GameState(newPlayerHp, newPlayerMana, newBossHp, newEffects);
        }
        
        public int getArmor() {
            return activeEffects.containsKey(Spell.SHIELD) && activeEffects.get(Spell.SHIELD) > 0 ? 7 : 0;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState that = (GameState) o;
            return playerHp == that.playerHp && 
                   playerMana == that.playerMana && 
                   bossHp == that.bossHp && 
                   activeEffects.equals(that.activeEffects);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(playerHp, playerMana, bossHp, activeEffects);
        }
    }

    public Day22() {
        super(22);
    }

    public static void main(String[] args) {
        Day22 day = new Day22();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 22, 2);
    }

    @Override
    public Object part1() {
        int[] bossStats = parseBoss();
        return findMinManaToWin(bossStats[0], bossStats[1]);
    }

    @Override
    public Object part2() {
        int[] bossStats = parseBoss();
        return findMinManaToWinHardMode(bossStats[0], bossStats[1]);
    }

    private int[] parseBoss() {
        List<String> lines = dayStream().collect(Collectors.toList());
        int hp = Integer.parseInt(lines.get(0).split(": ")[1]);
        int damage = Integer.parseInt(lines.get(1).split(": ")[1]);
        return new int[]{hp, damage};
    }

    private int findMinManaToWin(int bossHp, int bossDamage) {
        PriorityQueue<Map.Entry<GameState, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<GameState> visited = new HashSet<>();
        
        GameState initialState = new GameState(PLAYER_HP, PLAYER_MANA, bossHp, new HashMap<>());
        queue.offer(Map.entry(initialState, 0));
        
        while (!queue.isEmpty()) {
            var entry = queue.poll();
            GameState state = entry.getKey();
            int totalMana = entry.getValue();
            
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);
            
            // Player's turn - Apply effects first
            GameState afterEffects = state.applyEffects();
            if (afterEffects.bossHp <= 0) {
                return totalMana;
            }
            
            // Try each spell
            for (Spell spell : Spell.values()) {
                if (!afterEffects.canCastSpell(spell)) {
                    continue;
                }
                
                GameState afterSpell = afterEffects.castSpell(spell);
                if (afterSpell.bossHp <= 0) {
                    return totalMana + spell.cost;
                }
                
                // Boss's turn - Apply effects first
                GameState afterBossEffects = afterSpell.applyEffects();
                if (afterBossEffects.bossHp <= 0) {
                    return totalMana + spell.cost;
                }
                
                // Boss attacks
                int damage = Math.max(1, bossDamage - afterBossEffects.getArmor());
                GameState afterBossTurn = new GameState(
                    afterBossEffects.playerHp - damage,
                    afterBossEffects.playerMana,
                    afterBossEffects.bossHp,
                    afterBossEffects.activeEffects
                );
                
                if (afterBossTurn.playerHp > 0) {
                    queue.offer(Map.entry(afterBossTurn, totalMana + spell.cost));
                }
            }
        }
        
        return -1; // No solution found
    }

    private int findMinManaToWinHardMode(int bossHp, int bossDamage) {
        PriorityQueue<Map.Entry<GameState, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        Set<GameState> visited = new HashSet<>();
        
        GameState initialState = new GameState(PLAYER_HP, PLAYER_MANA, bossHp, new HashMap<>());
        queue.offer(Map.entry(initialState, 0));
        
        while (!queue.isEmpty()) {
            var entry = queue.poll();
            GameState state = entry.getKey();
            int totalMana = entry.getValue();
            
            if (visited.contains(state)) {
                continue;
            }
            visited.add(state);
            
            // Hard mode: Player loses 1 HP at start of turn
            GameState afterHardMode = new GameState(state.playerHp() - 1, state.playerMana(), state.bossHp(), state.activeEffects());
            if (afterHardMode.playerHp() <= 0) {
                continue;
            }
            
            // Player's turn - Apply effects first
            GameState afterEffects = afterHardMode.applyEffects();
            if (afterEffects.bossHp() <= 0) {
                return totalMana;
            }
            
            // Try each spell
            for (Spell spell : Spell.values()) {
                if (!afterEffects.canCastSpell(spell)) {
                    continue;
                }
                
                GameState afterSpell = afterEffects.castSpell(spell);
                if (afterSpell.bossHp() <= 0) {
                    return totalMana + spell.cost;
                }
                
                // Boss's turn - Apply effects first
                GameState afterBossEffects = afterSpell.applyEffects();
                if (afterBossEffects.bossHp() <= 0) {
                    return totalMana + spell.cost;
                }
                
                // Boss attacks
                int damage = Math.max(1, bossDamage - afterBossEffects.getArmor());
                GameState afterBossTurn = new GameState(
                    afterBossEffects.playerHp() - damage,
                    afterBossEffects.playerMana(),
                    afterBossEffects.bossHp(),
                    afterBossEffects.activeEffects()
                );
                
                if (afterBossTurn.playerHp() > 0) {
                    queue.offer(Map.entry(afterBossTurn, totalMana + spell.cost));
                }
            }
        }
        
        return -1; // No solution found
    }
}

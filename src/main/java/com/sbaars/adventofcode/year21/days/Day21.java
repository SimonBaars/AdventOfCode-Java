package com.sbaars.adventofcode.year21.days;

import com.google.common.base.Objects;
import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year21.Day2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day21 extends Day2021 implements HasRecursion {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  @Override
  public Object part1() {
    int player1 = 7;
    int player2 = 2;
    int score1= 0;
    int score2 = 0;
    int thr = 1;
    boolean turn = true;
    while(true){
      for(int j = 1; j<=3; j++) {
        if(turn){
          player1 = player1 + thr;
          while(player1>10) player1-=10;
        } else {
          player2 = player2 + thr;
          while(player2>10) player2-=10;
        }
        thr++;
      }
      if(turn) {
        score1+=player1;
      } else {
        score2+=player2;
      }
      if(score1 >= 1000 || score2 >= 1000){
        return Math.min(score1, score2) * (thr-1);
      }
      turn = !turn;
    }
  }

  @Override
  public Object part2() {
    Pair<Long, Long> universes = universes(new HashMap<>(), new State(7, 2, 0, 0, true));
    return Math.max(universes.getLeft(), universes.getRight());
  }

  private Pair<Long, Long> universes(Map<State, Pair<Long, Long>> m, State s) {
    Pair<Long, Long> wins = Pair.of(0L, 0L);
    if(m.containsKey(s)) {
      return m.get(s);
    } else if(Math.max(s.score[0], s.score[1]) >= 21){
      return Pair.of(s.move ? 0L : 1L, s.move ? 1L : 0L);
    }
    for(int i = 1; i<=3; i++){
      for(int j = 1; j<=3; j++){
        for(int k = 1; k<=3; k++){
          int newTurn = s.turn[s.move ? 0 : 1] + i + j + k;
          while(newTurn>10) newTurn-=10;
          int newScore = s.score[s.move ? 0 : 1] + newTurn;
          Pair<Long, Long> newWins = universes(m, new State(s.move ? newTurn : s.turn[0], s.move ? s.turn[1] : newTurn, s.move ? newScore : s.score[0], s.move ? s.score[1] : newScore, !s.move));
          wins = Pair.of(wins.getLeft() + newWins.getLeft(), wins.getRight() + newWins.getRight());
        }
      }
    }
    m.put(s, wins);
    return wins;
  }

  public record State(int[] turn, int[] score, boolean move) {
    public State (int t1, int t2, int s1, int s2, boolean move){
      this(new int[]{t1,t2}, new int[]{s1, s2}, move);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      State state = (State) o;
      return move == state.move && Arrays.equals(turn, state.turn) && Arrays.equals(score, state.score);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(turn[0], turn[1], score[0], score[1], move);
    }
  }
}

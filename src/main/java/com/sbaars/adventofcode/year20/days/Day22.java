package com.sbaars.adventofcode.year20.days;

import com.google.common.collect.Comparators;
import com.sbaars.adventofcode.year20.Day2020;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.LongStream;

import static java.lang.Math.toIntExact;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;
import static org.apache.commons.lang3.ArrayUtils.subarray;

public class Day22 extends Day2020 {
    public static void main(String[] args) {
        new Day22().printParts();
    }

    public Day22() {
        super(22);
    }

    @Override
    public Object part1() {
        String[] input = day().split("\n\n");
        Deque<Long> p1 = getInput(0, input);
        Deque<Long> p2 = getInput(1, input);

        while(p1.size()>0 && p2.size() > 0){
            long l1 = p1.pop();
            long l2 = p2.pop();
            if(l1 > l2){
                p1.add(l1);
                p1.add(l2);
            } else {
                p2.add(l2);
                p2.add(l1);
            }
        }
        Deque<Long> winner = p1.size()>0 ? p1 : p2;
        return calcScore(winner);
    }

    private long calcScore(Deque<Long> winner) {
        return LongStream.rangeClosed(1, winner.size()).boxed().sorted(Comparator.reverseOrder()).mapToLong(l -> winner.pop() * l).sum();
    }

    @Override
    public Object part2() {
        String[] input = day().split("\n\n");
        Deque<Long> p1 = getInput(0, input);
        Deque<Long> p2 = getInput(1, input);
        return calcScore(playGame(p1, p2) == Player.P1 ? p1 : p2);
    }

    private ArrayDeque<Long> getInput(int i, String[] input) {
        return Arrays.stream(input[i].split("\n")).filter(e -> !e.startsWith("Player")).map(Long::parseLong).collect(toCollection(ArrayDeque::new));
    }

    public Player playGame(Deque<Long> p1, Deque<Long> p2){
        Set<List<Long>> playedGames = new HashSet<>();
        while(p1.size()>0 && p2.size() > 0){
            if(!playedGames.add(new ArrayList<>(p1))){
                return Player.P1;
            }

            long l1 = p1.pop();
            long l2 = p2.pop();
            if(p1.size() < l1 || p2.size() < l2){
                if(l1 > l2){
                    p1.add(l1);
                    p1.add(l2);
                } else {
                    p2.add(l2);
                    p2.add(l1);
                }
            } else {
                if(playGame(new ArrayDeque<>(new ArrayList<>(p1).subList(0, toIntExact(l1))), new ArrayDeque<>(new ArrayList<>(p2).subList(0, toIntExact(l2)))) == Player.P1){
                    p1.add(l1);
                    p1.add(l2);
                } else {
                    p2.add(l2);
                    p2.add(l1);
                }
            }
        }
        return p1.size()>0 ? Player.P1 : Player.P2;
    }

    enum Player{P1, P2}
}

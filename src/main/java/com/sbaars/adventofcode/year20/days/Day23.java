package com.sbaars.adventofcode.year20.days;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.sbaars.adventofcode.common.SmartArray;
import com.sbaars.adventofcode.common.CircularLinkedList;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static com.google.common.primitives.Ints.asList;

public class Day23 extends Day2020 {
    public static void main(String[] args) {
        //new Day23().printParts();
        System.out.println(new Day23().part1());
    }

    public Day23() {
        super(23);
    }

    @Override
    public Object part1() {
        return getSolution(true);
    }

    @Override
    public Object part2() {
        return getSolution(false);
    }

    private String getSolution(boolean part1) {
        //int[] input = {4,9,6,1,3,8,5,2,7};
        int[] input = {3,8,9,1,2,5,4,6,7};
        CircularLinkedList cups = new CircularLinkedList(Streams.concat(Arrays.stream(input), part1 ? IntStream.empty() : IntStream.rangeClosed(10,1000000)).toArray());
        for(int i = 0; i<=(part1 ? 100 : 10000000); i++){
            System.out.println("\nRound "+(i+1));
            int current = cups.current();
            System.out.println(Arrays.toString(cups.toArray()).replace(Integer.toString(current), "("+current+")"));
            int j;
            int[] toMove = cups.nextRev(3);
            System.out.println("Pick up "+Arrays.toString(cups.next(3)));
            for(j = current - 2 + cups.size(); j>0; j--){
                if(!asList(toMove).contains(j % cups.size() + 1)){
                    break;
                }
            }
            int d = j % cups.size() + 1;
            System.out.println("Dest: "+(j % cups.size() + 1));
            for(int move : toMove) {
                cups.insertAfter(move, d);
            }
            cups.next();
        }
        cups.setCurrent(1);
        return Arrays.stream(cups.next(part1 ? 8 : 2)).mapToObj(Integer::toString).collect(Collectors.joining());
    }
}

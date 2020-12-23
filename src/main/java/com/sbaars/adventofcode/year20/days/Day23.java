package com.sbaars.adventofcode.year20.days;

import com.google.common.collect.Streams;
import com.sbaars.adventofcode.common.CircularLinkedList;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.primitives.Ints.asList;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;

public class Day23 extends Day2020 {
    public static void main(String[] args) {
        new Day23().printParts();
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

    private long getSolution(boolean part1) {
        int[] input = day().chars().map(Character::getNumericValue).toArray();
        CircularLinkedList cups = new CircularLinkedList(Streams.concat(stream(input), part1 ? IntStream.empty() : IntStream.rangeClosed(10,1000000)).toArray());
        long mil = System.currentTimeMillis();
        for(int i = 0; i<(part1 ? 100 : 10000000); i++){
            int current = cups.current();
            int j;
            CircularLinkedList.Node next = cups.currentNode().next;
            CircularLinkedList.Node last = next.next.next;
            for(j = current - 2 + cups.size(); j>0; j--){
                int n = j % cups.size() + 1;
                if(next.value != n && next.next.value != n && last.value != n){
                    break;
                }
            }
            int d = j % cups.size() + 1;
            cups.insertAfter(next, last, d);
            cups.next();
        }
        System.out.println((System.currentTimeMillis() - mil));
        cups.setCurrent(1);
        if(part1) return parseLong(stream(cups.next(8)).mapToObj(Integer::toString).collect(Collectors.joining()));
        int[] next = cups.next(2);
        return (long)next[0] * next[1];
    }
}

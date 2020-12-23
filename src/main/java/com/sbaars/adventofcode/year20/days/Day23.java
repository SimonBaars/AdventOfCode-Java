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
//        int[] input = {4,9,6,1,3,8,5,2,7};
//        LinkedList<Integer> cups = Arrays.stream(input).boxed().collect(Collectors.toCollection(LinkedList::new));
//        int index = 0;
//        for(int i = 0; i<100; i++){
//            System.out.println("\nRound "+(i+1));
//            int current = cups.get(index);
//            System.out.println(Arrays.toString(cups.toArray()).replace(Integer.toString(current), "("+current+")"));
//            int rem1 = cups.remove((index+1)%cups.size());
//            index = cups.indexOf(current);
//            int rem2 = cups.remove((index+1)%cups.size());
//            index = cups.indexOf(current);
//            int rem3 = cups.remove((index+1)%cups.size());
//            System.out.println("Pick up "+rem1+", "+rem2+", "+rem3);
//            int j;
//            for(j = current - 2 + input.length; j>0; j--){
//                if(cups.contains(j % input.length + 1)){
//                    break;
//                }
//            }
//            int dest = cups.indexOf(j % input.length + 1);
//            System.out.println("Dest: "+(j % input.length + 1)+", "+dest);
//            cups.add(dest+1, rem3);
//            cups.add(dest+1, rem2);
//            cups.add(dest+1, rem1);
//            index = cups.indexOf(current);
//            index = (index + 1) % cups.size();
//        }
        return getSolution(true);

        //return 0;
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
//            cups.move(index.in, dest+1);
//            cups.move((index+2)%cups.size(), dest+1);
//            cups.move((index+1)%cups.size(), dest+1);
//            index = cups.indexOf(current);
//            index = (index + 1) % cups.size();
            cups.next();
//            if(i%100000==0){
//                System.out.println(i);
//            }
//            if(i>9999998){
//                System.out.println("\nRound "+(i+1));
//                System.out.println(Arrays.toString(cups.toArray()).replace(Integer.toString(current), "("+current+")"));
//            }
        }
        cups.setCurrent(1);
        return Arrays.stream(cups.next(part1 ? 8 : 2)).mapToObj(Integer::toString).collect(Collectors.joining());
    }
}

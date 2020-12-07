package com.sbaars.adventofcode.haskell.year20;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import com.sbaars.adventofcode.year20.Day2020;
import com.sbaars.adventofcode.year20.days.Day7.Trade;
import com.sbaars.adventofcode.year20.days.Day7.Item;
import java.util.Arrays;

public class Day7 extends Day2020 {
    public Day7() {
        super(7);
    }

    public static void main(String[] args)  {
        new Day7().printParts();
    }

    @Override
    public Object part1()  {
        Trade[] trades =
                dayStream().filter(s -> !s.contains("no other bags"))
                        .map(Trade::new)
                        .toArray(Trade[]::new);;
        return stream(trades)
                .map(t -> "(\""+t.input.item+"\", "+stream(t.output).map(this::convertItem).collect(joining(", ", "[", "]"))+")")
                .collect(joining(", ", "[", "]"));
    }

    private String convertItem(Item i){
        return "("+i.amount+", \""+i.item+"\")";
    }

    @Override
    public Object part2()  {
        return null;
    }
}

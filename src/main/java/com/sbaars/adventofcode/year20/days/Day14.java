package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import lombok.Data;
import lombok.Value;

import java.util.*;
import org.apache.commons.text.TextStringBuilder;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.Long.toBinaryString;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

public class Day14 extends Day2020 {
    public static void main(String[] args) {
        new Day14().printParts();
    }

    public Day14() {
        super(14);
    }

    @Override
    public Object part1() {
        Instruction[] input = getInput();
        Map<Long, Long> memory = new HashMap<>();
        final TextStringBuilder currentMask = new TextStringBuilder();
        for(Instruction i : input){
            i.getMem().ifPresentOrElse(m -> memory.put(m.index,  m.value | parseLong(currentMask.toString(), 2)),
                    () -> currentMask.set(i.value).replaceAll("X", "0"));
        }
        return memory.values().stream().mapToLong(e -> e).sum();
    }

    @Data
    @Value
    public static class Instruction {
        String mem;
        String value;

        public Optional<Mem> getMem(){
            return mem.startsWith("mem") ? of(readString(mem+value, "mem[%n]%n", Mem.class)) : empty();
        }
    }

    @Data
    @Value
    public static class Mem {
        long index;
        long value;
    }

    @Override
    public Object part2() {
        Instruction[] input = getInput();
        Map<Long, Long> memory = new HashMap<>();
        String currentMask = "";
        for(Instruction i : input){
            Optional<Mem> mem = i.getMem();
            if(mem.isPresent()){
                StringBuilder bin = binWithLength(mem.get().index, currentMask.length());
                List<Integer> floaters = applyMask(currentMask, bin);
                fillMemory(memory, mem, bin, floaters);
            } else {
                currentMask = i.value;
            }
        }
        return memory.values().stream().mapToLong(e -> e).sum();
    }

    private Instruction[] getInput() {
        return dayStream().map(s -> readString(s, "%s = %s", Instruction.class)).toArray(Instruction[]::new);
    }

    private void fillMemory(Map<Long, Long> memory, Optional<Mem> mem, StringBuilder bin, List<Integer> floaters) {
        StringBuilder binary;
        for(long j = 0; (binary = binWithLength(j, floaters.size())).length() == floaters.size(); j++){
            for(int k = 0; k< floaters.size(); k++){
                bin.setCharAt(floaters.get(k), binary.charAt(k));
            }
            memory.put(parseLong(bin.toString(), 2), mem.get().value);
        }
    }

    private List<Integer> applyMask(String currentMask, StringBuilder bin) {
        List<Integer> floaters = new ArrayList<>();
        for(int j = 0; j< bin.length(); j++){
            char c = currentMask.charAt(j);
            if(c=='X') floaters.add(j);
            else if(c == '1') bin.setCharAt(j, c);
        }
        return floaters;
    }

    private StringBuilder binWithLength(long val, int s) {
        StringBuilder bin = new StringBuilder(toBinaryString(val));
        while (bin.length() < s) {
            bin.insert(0, '0');
        }
        return bin;
    }
}

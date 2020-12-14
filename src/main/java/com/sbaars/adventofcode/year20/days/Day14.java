package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import lombok.Data;
import lombok.Value;

import java.util.*;

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
        Instruction[] input = dayStream().map(s -> readString(s, "%s = %s", Instruction.class)).toArray(Instruction[]::new);
        Map<Long, Long> memory = new HashMap<>();
        String currentMask = "";
        for(Instruction i : input){
            Optional<Mem> mem = i.getMem();
            if(mem.isPresent()){
                long val = mem.get().value;
                StringBuilder bin = new StringBuilder(toBinaryString(val));
                while(bin.length()<currentMask.length()){
                    bin.insert(0, '0');
                }
                for(int j = 1; j<=bin.length(); j++){
                    if(j<=currentMask.length() && currentMask.charAt(currentMask.length()-j)!='X'){
                        bin.setCharAt(bin.length()-j, currentMask.charAt(currentMask.length()-j));
                    }
                }
                memory.put(mem.get().index, parseLong(bin.toString(), 2));
            } else {
                currentMask = i.value;
            }
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
        Instruction[] input = dayStream().map(s -> readString(s, "%s = %s", Instruction.class)).toArray(Instruction[]::new);
        Map<Long, Long> memory = new HashMap<>();
        String currentMask = "";
        for(Instruction i : input){
            Optional<Mem> mem = i.getMem();
            if(mem.isPresent()){
                long val = mem.get().index;
                StringBuilder bin = binWithLength(val, currentMask.length());
                List<Integer> floaters = new ArrayList<>();
                for(int j = 1; j<=bin.length(); j++){
                    char c = currentMask.charAt(currentMask.length()-j);
                    char c2 = bin.charAt(bin.length()-j);
                    if(c!='X'){
                        if(c == c2){
                            floaters.add(j);
                        } else if(c == '1'){
                            bin.setCharAt(bin.length()-j, c);
                        }
                    }
                }
                StringBuilder binary;
                long j = 0;
                while((binary = binWithLength(j, floaters.size())).length() == floaters.size()){
                    for(int k = 0; k<floaters.size(); k++){
                        bin.setCharAt(bin.length()-floaters.get(k), binary.charAt(k));
                    }
                    memory.put(parseLong(bin.toString(), 2), mem.get().value);
                    j++;
                }

            } else {
                currentMask = i.value;
            }
        }
        return memory.values().stream().mapToLong(e -> e).sum();
    }

    private StringBuilder binWithLength(long val, int s) {
        StringBuilder bin = new StringBuilder(toBinaryString(val));
        while (bin.length() < s) {
            bin.insert(0, '0');
        }
        return bin;
    }
}

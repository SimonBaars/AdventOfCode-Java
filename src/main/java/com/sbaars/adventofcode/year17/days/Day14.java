package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.common.Day;

public class Day14 extends Day {
    public Day14() {
        super(14, 2017);
    }

    public static void main(String[] args) {
        new Day14().printParts();
    }

    @Override
    public Object part1() {
        String input = "hxtvlmkl";
        int usedSquares = 0;
        
        for (int i = 0; i < 128; i++) {
            String rowInput = input + "-" + i;
            String hash = knotHash(rowInput);
            usedSquares += countBits(hash);
        }
        
        return usedSquares;
    }

    private String knotHash(String input) {
        int[] list = new int[256];
        for (int i = 0; i < 256; i++) {
            list[i] = i;
        }
        
        int[] lengths = new int[input.length() + 5];
        for (int i = 0; i < input.length(); i++) {
            lengths[i] = input.charAt(i);
        }
        lengths[input.length()] = 17;
        lengths[input.length() + 1] = 31;
        lengths[input.length() + 2] = 73;
        lengths[input.length() + 3] = 47;
        lengths[input.length() + 4] = 23;
        
        int pos = 0;
        int skip = 0;
        
        for (int round = 0; round < 64; round++) {
            for (int length : lengths) {
                // Reverse the sublist
                for (int i = 0; i < length / 2; i++) {
                    int a = (pos + i) % 256;
                    int b = (pos + length - 1 - i) % 256;
                    int temp = list[a];
                    list[a] = list[b];
                    list[b] = temp;
                }
                pos = (pos + length + skip) % 256;
                skip++;
            }
        }
        
        // Calculate dense hash
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int xor = 0;
            for (int j = 0; j < 16; j++) {
                xor ^= list[i * 16 + j];
            }
            result.append(String.format("%02x", xor));
        }
        
        return result.toString();
    }

    private int countBits(String hash) {
        int count = 0;
        for (char c : hash.toCharArray()) {
            int value = Character.digit(c, 16);
            count += Integer.bitCount(value);
        }
        return count;
    }

    @Override
    public Object part2() {
        return 0;
    }
}

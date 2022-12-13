package com.sbaars.adventofcode.util;

import com.sbaars.adventofcode.common.IntArray;

import java.util.stream.Collectors;

public class StringUtils {
    public static String[] lines(String s) {
        return s.split("\n");
    }

    public static String removeAll(String str, String remove) {
        var arr = new IntArray(str.chars());
        remove.chars().forEach(arr::removeElement);
        return arr.stream().mapToObj(Character::toString).collect(Collectors.joining());
    }

    public static boolean charSubset(String a, String b){
        return a.chars().allMatch(c -> b.chars().anyMatch(d -> d == c));
    }
}

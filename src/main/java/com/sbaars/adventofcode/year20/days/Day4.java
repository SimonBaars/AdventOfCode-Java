package com.sbaars.adventofcode.year20.days;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.lang.Math.toIntExact;
import static org.apache.commons.collections4.CollectionUtils.isEqualCollection;

import com.google.common.collect.Maps;
import com.sbaars.adventofcode.common.ReadsFormattedString;
import com.sbaars.adventofcode.year20.Day2020;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.Value;

public class Day4 extends Day2020 implements ReadsFormattedString {
    private static final Map<String, String> expected = Map.of("byr", "^(200[0-2]|19[2-9][0-9])$",
                                                                "iyr", "^(2020|201[0-9])$",
                                                                "eyr", "^(2030|202[0-9])$",
                                                                "hgt", "^((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-6])in))$",
                                                                "hcl", "^(#[0-9a-f]{6})$",
                                                                "ecl", "^(amb|blu|brn|gry|grn|hzl|oth)$",
                                                                "pid", "^[0-9]{9}$");

    public static void main(String[] args)  {
        new Day4().printParts();
    }

    public Day4(){super(4);}

    @Override
    public Object part1()  {
        return verifyPassports(this::valid1);
    }

    @Override
    public Object part2()  {
        return verifyPassports(this::valid2);
    }

    public long verifyPassports(Predicate<String[]> verifyFunction){
        String[][] passports = Arrays.stream(day().split("\n\n")).map(str -> str.replace("\n", " ")).map(str -> str.split(" ")).toArray(String[][]::new);
        return Arrays.stream(passports).filter(verifyFunction).count();
    }

    public boolean valid1(String[] passport){
        return Arrays.stream(passport).map(s -> s.substring(0, 3)).collect(toImmutableSet()).containsAll(expected.keySet());
    }

    public boolean valid2(String[] passport){
        return valid1(passport) && Arrays.stream(passport).map(s -> s.split(":")).allMatch(s -> matchesRegex(s[0], s[1]));
    }

    public boolean matchesRegex(String key, String validate){
        if(!expected.containsKey(key)) return true;
        final Pattern pattern = Pattern.compile(expected.get(key));
        return pattern.matcher(validate).matches();
    }
}

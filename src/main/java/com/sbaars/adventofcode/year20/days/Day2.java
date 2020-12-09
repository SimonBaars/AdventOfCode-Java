package com.sbaars.adventofcode.year20.days;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.lang.Math.toIntExact;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Value;

public class Day2 extends Day2020 {
    public static void main(String[] args) {
        new Day2().printParts();
    }

    public Day2() {
        super(2);
    }

    @Override
    public Object part1() {
        return pwCount(Password::isValid);
    }

    private long pwCount(Predicate<Password> validator) {
        List<Password> s = dayStream().map(Day2::mapPassword).collect(Collectors.toList());
        return s.stream().filter(validator).count();
    }

    public static Password mapPassword(String s) {
        return readString(s, "%n-%n %c: %s", Password.class);
    }

    @Value
    @Data
    public static class Password {
        long lower;
        long higher;
        char character;
        String password;

        public boolean isValid() {
            long count = password.chars().filter(c -> c == character).count();
            return count >= lower && count <= higher;
        }

        public boolean isValid2() {
            int char1 = password.charAt(toIntExact(lower - 1));
            int char2 = password.charAt(toIntExact(higher - 1));
            return char1 != char2 && (char1 == character || char2 == character);
        }
    }

    @Override
    public Object part2() {
        return pwCount(Password::isValid2);
    }
}

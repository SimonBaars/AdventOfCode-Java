package com.sbaars.adventofcode.year24.days;

import java.util.*;
import java.util.stream.Stream;

import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.Direction3D.six;
import static java.lang.Character.toLowerCase;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.LongStream.range;

/**
 * This is the Infi AoC challenge, from: https://aoc.infi.nl/2024
 */

public class Day0 extends Day2024 {
    private final int LENGTH = 30;

    public Day0() {
        super(0);
    }

    public static void main(String[] args) {
        new Day0().printParts();
    }

    public record Instruction(String type, long n, char dimension) {
        public Instruction(String s) {
            this(s.split(" ")[0],
                    s.contains(" ") && !isLetter(s.charAt(s.indexOf(' ') + 1)) ? parseLong(s.split(" ")[1]) : 0,
                    s.contains(" ") && isLetter(s.charAt(s.indexOf(' ') + 1)) ? s.charAt(s.indexOf(' ') + 1) : 0);
        }
    }

    @Override
    public Object part1() {
        return calcGrid().values().stream().mapToLong(Long::longValue).sum();
    }

    @Override
    public Object part2() {
        var clouds = calcGrid().keySet().stream().filter(l -> g.get(l) > 0).toList();
        return count(clouds);
    }

    private Stream<Loc3D> allPositions() {
        return range(0, LENGTH).boxed().flatMap(
                x -> range(0, LENGTH).boxed().flatMap(y -> range(0, LENGTH).boxed().map(z -> new Loc3D(x, y, z))));
    }

    private Map<Loc3D, Long> calcGrid() {
        var in = dayStream().map(Instruction::new).toList();
        return allPositions().collect(toMap(l -> l, l -> runProgram(l, in)));
    }

    private static Long runProgram(Loc3D l, List<Instruction> in) {
        var q = new LinkedList<Long>();
        for (int c = 0; c < in.size(); c++) {
            var inst = in.get(c);
            switch (inst.type()) {
                case "push" -> q.push(switch (toLowerCase(inst.dimension)) {
                    case 'x' -> l.x;
                    case 'y' -> l.y;
                    case 'z' -> l.z;
                    default -> inst.n;
                });
                case "add" -> q.push(q.pop() + q.pop());
                case "jmpos" -> {
                    if (q.pop() >= 0)
                        c += inst.n;
                }
                case "ret" -> {
                    return q.pop();
                }
            }
        }
        throw new IllegalStateException("Program didn't return");
    }

    private static void dfs(Loc3D start, Set<Loc3D> locs) {
        var stack = new LinkedList<>(List.of(start));
        while (!stack.isEmpty()) {
            Loc3D current = stack.pop();
            six().map(d -> d.move(current)).filter(locs::remove).forEach(stack::push);
        }
    }

    public static long count(List<Loc3D> locs) {
        Set<Loc3D> set = new HashSet<>(locs);
        return locs.stream().filter(set::contains).peek(l -> dfs(l, set)).count();
    }

    private static boolean isLetter(char c) {
        return toLowerCase(c) >= 'x' && toLowerCase(c) <= 'z';
    }
}

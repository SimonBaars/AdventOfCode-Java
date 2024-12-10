package com.sbaars.adventofcode.year24.days;

import java.util.*;

import com.sbaars.adventofcode.common.location.Loc3D;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.Direction3D.six;
import static java.lang.Character.toLowerCase;
import static java.lang.Long.parseLong;
import static java.lang.Math.toIntExact;
import static java.util.stream.LongStream.range;

/**
 * This is the Infi AoC challenge, from: https://aoc.infi.nl/2024
 */

public class Day0 extends Day2024 {
    public Day0() {
        super(0);
    }

    public static void main(String[] args) {
        var d = new Day0();
//        d.example = 1;
        d.printParts();
    }

    public record Pair(int a, int b) {}

    private static boolean isLetter(char c) {
        return toLowerCase(c) >= 'x' && toLowerCase(c) <= 'z';
    }

    public record Instruction(String type, long n, char dimension) {
        public Instruction(String s) {
            this(
                    s.split(" ")[0],
                    s.contains(" ") && !isLetter(s.charAt(s.indexOf(' ') + 1)) ? parseLong(s.split(" ")[1]) : 0,
                    s.contains(" ") && isLetter(s.charAt(s.indexOf(' ') + 1)) ? s.charAt(s.indexOf(' ') + 1) : 0
            );
        }
    }

    @Override
    public Object part1() {
        Long[][][] g = calcGrid();
        return Arrays.stream(g)
                .flatMap(Arrays::stream)
                .flatMap(Arrays::stream)
                .mapToLong(Long::longValue)
                .sum();
    }

    private Long[][][] calcGrid() {
        var in = dayStream().map(Instruction::new).toList();
        var q = new LinkedList<Long>();
        Long[][][] g = new Long[30][30][30];
        for(long x = 0; x<g.length; x++){
            for(long y = 0; y<g.length; y++){
                for(long z = 0; z<g.length; z++){
                    q.clear();
                    long c = 0;
                    whileloop: while(c<in.size()) {
                        var inst = in.get(toIntExact(c));
                        switch(inst.type()) {
                            case "push": q.push(switch(toLowerCase(inst.dimension)) {
                                case 'x' -> x;
                                case 'y' -> y;
                                case 'z' -> z;
                                default -> inst.n;
                            }); break;
                            case "add": q.push(q.pop() + q.pop()); break;
                            case "jmpos": if(q.pop() >= 0) { c += inst.n; } break;
                            case "ret": g[toIntExact(z)][toIntExact(y)][toIntExact(x)] = q.pop(); break whileloop;
                        }
                        c++;
                    }
                }
            }
        }
        return g;
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

    @Override
    public Object part2() {
        Long[][][] g = calcGrid();
        var coords = range(0, g.length).boxed().flatMap(x -> range(0, g[0].length).boxed().flatMap(y -> range(0, g[0][0].length).boxed().map(z -> new Loc3D(x, y, z))))
                .filter(l -> g[l.intX()][l.intY()][l.intZ()] > 0)
                .toList();
        return count(coords);
    }
}

package com.sbaars.adventofcode.year20.days;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day19 extends Day2020 {
    public static void main(String[] args) {
        new Day19().printParts();
    }

    Map<Long, Set<String>> sol = new HashMap<>();

    public Day19() {
        super(19);
    }

    @Override
    public Object part1() {
        return getSolution(day());
    }

    private long getSolution(String inputFile) {
        String[] input = inputFile.split("\n\n");
        Map<Long, Rule> rules = Arrays.stream(input[0].split("\n"))
                .map(e -> e.split(": "))
                .collect(Collectors.toMap(e -> parseLong(e[0]), e -> new Rule(parseLong(e[0]), e[1])));

        rules.values().forEach(e -> e.getPossibilities(rules, sol));
        return stream(input[1].split("\n"))
                .filter(e -> sol.values().stream().anyMatch(r -> r.contains(e)))
                .count();
    }

    private static Optional<String> getLetter(String rule){
        return rule.startsWith("\"") ? Optional.of(rule.charAt(1)+"") : Optional.empty();
    }

    private static long[] getRule(String rule, boolean num){
        if(rule.startsWith("\"")){
            return new long[]{};
        }
        String[] r = rule.split(" \\| ");
        if (!num || r.length > 1) {
            return Arrays.stream(r[num ? 1 : 0].split(" ")).mapToLong(Long::parseLong).toArray();
        } else {
            return new long[]{};
        }
    }

    public static record Rule (long id, Optional<String> letter, long[] rule1, long[] rule2) {
        public Rule(long id, String rule){
            this(id, getLetter(rule), getRule(rule, false), getRule(rule, true));
        }

        public Set<String> getPossibilities(Map<Long, Rule> m, Map<Long, Set<String>> sol){
            if(sol.containsKey(id)) return sol.get(id);
            if(letter.isEmpty()){
                Rule[] r = stream(rule1).mapToObj(m::get).toArray(Rule[]::new);
                Rule[] orRule = stream(rule2).mapToObj(m::get).toArray(Rule[]::new);
                Set<String> output = r[0].getPossibilities(m, sol);
                if(sol.containsKey(id)) return sol.get(id);
                for(int i = 1; i<r.length; i++){
                    Set<String> output2 = r[i].getPossibilities(m, sol);
                    if(sol.containsKey(id)) return sol.get(id);
                    Set<String> newOne = new HashSet<>();
                    for(String o : output){
                        for(String o2 : output2){
                            newOne.add(o+o2);
                        }
                    }
                    output = newOne;
                }
                if(orRule.length>0){
                    Set<String> outputOr = orRule[0].getPossibilities(m, sol);
                    if(sol.containsKey(id)) return sol.get(id);
                    for(int i = 1; i<orRule.length; i++){
                        Set<String> outputOr2 = orRule[i].getPossibilities(m, sol);
                        if(sol.containsKey(id)) return sol.get(id);
                        Set<String> newOne = new HashSet<>();
                        for(String o : outputOr){
                            for(String o2 : outputOr2){
                                newOne.add(o+o2);
                            }
                        }
                        outputOr = newOne;
                    }
                    output.addAll(outputOr);
                }
                sol.put(id, output);
                return output;
            }
            return new HashSet<>(singletonList(letter.get()));
        }
    }

    @Override
    public Object part2() {
//        int maxDepth = 2;
//        StringBuilder s = new StringBuilder("8: 42 ");
//        StringBuilder s2 = new StringBuilder("11: 42 31 ");
//        for(int i = 2; i<=maxDepth; i++){
//            s.append("| ").append(i + 198).append("\n").append(i + 198).append(": ").append("42 ".repeat(i));
//            s2.append("| ").append(i + 298).append("\n").append(i + 298).append(": ").append("42 ".repeat(i)).append("31 ".repeat(i));
//
//        }
//        return getSolution(day()
//                .replace("8: 42", s.toString().trim())
//                .replace("11: 42 31", s2.toString().trim()));
        int maxDepth = 10;
        String[] input = day().split("\n\n");
        Set<String> all = sol.values().stream().flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
        Set<String> s42 = sol.get(42L);
        Set<String> s31 = sol.get(31L);
        Set<String> s11 = sol.get(11L);
        for(int i = 2; i<=maxDepth; i++){
            Set<String> add = new HashSet<>(s42.size() * s31.size());
            Set<String> add2 = new HashSet<>(s42.size() * s31.size() * s11.size());
//            for(int i = 0; i<s42.size(); i++){
//                add.add(s+s);
//            }
//            for(String s : s31){
//                add.add(s+s);
//            }

            for(String o : s42){
                for(String o1 : s42){
                    add.add(o+o1);
                }
                for(String o2 : s31){
                    for(String o3 : s11) {
                        add2.add(o + o3 + o2);
                    }
                }
            }
            s42.addAll(add);
            s11.addAll(add2);
        }
        return stream(input[1].split("\n"))
                .filter(e -> sol.values().stream().anyMatch(r -> r.contains(e)))
                .count();
    }
}

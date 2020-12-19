package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.year20.Day2020;
import lombok.Data;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class Day19 extends Day2020 {
    public static void main(String[] args) {
        new Day19().printParts();
    }

    public Day19() {
        super(19);
    }

    @Override
    public Object part1() {
        return getSolution(day());
    }

    private long getSolution(String inputFile) {
        String[] input = inputFile.split("\n\n");
        int longest = stream(input[1].split("\n")).mapToInt(e -> e.length()).max().getAsInt();
        Map<Long, Rule> rules = Arrays.stream(input[0].split("\n")).map(e -> e.split(": ")).collect(Collectors.toMap(e -> parseLong(e[0]), e -> new Rule(parseLong(e[0]), e[1])));
        Map<Long, Set<String>> sol = new HashMap<>();
        rules.values().stream().peek(e -> System.out.println(e.id)).forEach(e -> e.getPossibilities(rules, sol, longest, 0));
        return stream(input[1].split("\n")).peek(System.out::println).filter(e -> sol.values().stream().anyMatch(r -> r.contains(e))).count();
    }

    @Data
    @Value
    public static class Rule{
        long id;
        Optional<String> letter;
        long[] rule1;
        long[] rule2;

        public Rule(long id, String rule){
            this.id = id;
            if(rule.startsWith("\"")){
                letter = Optional.of(rule.charAt(1)+"");
                rule2 = new long[]{};
                rule1 = new long[]{};
            } else {
                letter = Optional.empty();
                String[] r = rule.split(" \\| ");
                rule1 = Arrays.stream(r[0].split(" ")).mapToLong(Long::parseLong).toArray();
                if (r.length > 1) {
                    rule2 = Arrays.stream(r[1].split(" ")).mapToLong(Long::parseLong).toArray();
                } else {
                    rule2 = new long[]{};
                }
            }
        }

        public Set<String> getPossibilities(Map<Long, Rule> m, Map<Long, Set<String>> sol, int longest, int depth){
            if(sol.containsKey(id)) return sol.get(id);
            if(!letter.isPresent()){
                Rule[] r = stream(rule1).mapToObj(n -> m.get(n)).toArray(Rule[]::new);
                Rule[] orRule = stream(rule2).mapToObj(n -> m.get(n)).toArray(Rule[]::new);
                Set<String> output = r[0].getPossibilities(m, sol, longest, depth);
                for(int i = 1; i<r.length; i++){
                    Set<String> output2 = r[i].getPossibilities(m, sol, longest, depth);
                    Set<String> newOne = new HashSet<>();
                    for(String o : output){
                        for(String o2 : output2){
                            newOne.add(o+o2);
                        }
                    }
                    output = newOne;
                }
                if(orRule.length>0 && output.stream().findFirst().get().length()<=longest && depth<4){
                    Set<String> outputOr = orRule[0].getPossibilities(m, sol, longest, depth);
                    for(int i = 1; i<orRule.length; i++){
                        Set<String> outputOr2 = orRule[i].getPossibilities(m, sol, longest, id == orRule[i].id ? depth + 1 : depth);
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
            return new HashSet<>(asList(letter.get()));
        }
    }

    @Override
    public Object part2() {
        return getSolution(day().replace("8: 42", "8: 42 | 42 8").replace("11: 42 31", "11: 42 31 | 42 11 31"));
    }
}

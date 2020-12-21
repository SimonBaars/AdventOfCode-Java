package com.sbaars.adventofcode.year20.days;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static java.util.Arrays.asList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sbaars.adventofcode.year20.Day2020;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Value;

public class Day21 extends Day2020 {
    public static void main(String[] args) {
        new Day21().printParts();
    }

    public Day21() {
        super(21);
    }

    @Override
    public Object part1() {
//        Rule[] input = Arrays.stream(day().split("\n")).map(Rule::new).toArray(Rule[]::new);
//        Multimap<String, String> allergens = ArrayListMultimap.create();
//        Set<String> found = new HashSet<>();
//        for(Rule r : input){
//            for(String ingredient : r.ingredients){
//                if(!found.contains(ingredient)){
//                    allergens.putAll(ingredient, asList(r.allergens));
//                } else if(allergens.containsKey(ingredient)) {
//                    Collection<String> a = new ArrayList<>(allergens.get(ingredient));
//                    for(String s : a){
//                        if(!asList(r.allergens).contains(s)){
//                            allergens.remove(ingredient, s);
//                        }
//                    }
//                }
//                found.add(ingredient);
//                System.out.println(allergens.size());
//            }
//        }
//
//        long total = 0;
//        for(Rule r : input) {
//            for (String ingredient : r.ingredients) {
//                if(!allergens.containsKey(ingredient)){
//                    total++;
//                }
//            }
//        }

        Rule[] input = Arrays.stream(day().split("\n")).map(Rule::new).toArray(Rule[]::new);
        Multimap<String, String> allergens = ArrayListMultimap.create();
        Set<String> found = new HashSet<>();
        for(Rule r : input){
            for(String allergen : r.allergens){
                if(!found.contains(allergen)){
                    allergens.putAll(allergen, asList(r.ingredients));
                } else if(allergens.containsKey(allergen)) {
                    Collection<String> a = new ArrayList<>(allergens.get(allergen));
                    for(String s : a){
                        if(!asList(r.ingredients).contains(s)){
                            allergens.remove(allergen, s);
                        }
                    }
                }
                found.add(allergen);
            }
        }

        long total = 0;
        for(Rule r : input) {
            for (String ingredient : r.ingredients) {
                if(!allergens.containsValue(ingredient)){
                    total++;
                }
            }
        }
        return total;
    }

    @Data
    @Value
    public static class Rule {
        String[] ingredients;
        String[] allergens;

        public Rule (String s){
            String[] split = s.substring(0, s.length()-1).split(" \\(contains ");
            this.ingredients = split[0].split(" ");
            this.allergens = split[1].split(", ");
        }
    }

    @Override
    public Object part2() {
        Rule[] input = Arrays.stream(day().split("\n")).map(Rule::new).toArray(Rule[]::new);
        Multimap<String, String> allergens = ArrayListMultimap.create();
        Set<String> found = new HashSet<>();
        for(Rule r : input){
            for(String allergen : r.allergens){
                if(!found.contains(allergen)){
                    allergens.putAll(allergen, asList(r.ingredients));
                } else if(allergens.containsKey(allergen)) {
                    Collection<String> a = new ArrayList<>(allergens.get(allergen));
                    for(String s : a){
                        if(!asList(r.ingredients).contains(s)){
                            allergens.remove(allergen, s);
                        }
                    }
                }
                found.add(allergen);
            }
        }


        return allergens.asMap().entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(e -> e.getValue().stream().findAny().get()).collect(Collectors.joining(","));
    }
}

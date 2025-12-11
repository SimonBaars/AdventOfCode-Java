package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day11 extends Day2025 {
    public Day11() {
        super(11);
    }

    public static void main(String[] args) throws IOException {
        new Day11().printParts();
    }

    @Override
    public Object part1() {
        Map<String, List<String>> graph = parseGraph();
        return countPaths(graph, "you", "out", new HashMap<>());
    }

    @Override
    public Object part2() {
        Map<String, List<String>> graph = parseGraph();
        Map<String, Long> memo = new HashMap<>();
        
        // Count paths from svr to out that visit both dac and fft
        // Two cases: dac before fft, or fft before dac
        
        // Case 1: svr -> dac -> fft -> out
        long case1 = countPathsVia(graph, "svr", "dac", "fft", "out");
        
        // Case 2: svr -> fft -> dac -> out
        long case2 = countPathsVia(graph, "svr", "fft", "dac", "out");
        
        return case1 + case2;
    }
    
    private long countPathsVia(Map<String, List<String>> graph, String start, String via1, String via2, String end) {
        // Count paths: start -> via1 -> via2 -> end
        Map<String, Long> memo1 = new HashMap<>();
        Map<String, Long> memo2 = new HashMap<>();
        Map<String, Long> memo3 = new HashMap<>();
        
        long paths1 = countPaths(graph, start, via1, memo1);
        long paths2 = countPaths(graph, via1, via2, memo2);
        long paths3 = countPaths(graph, via2, end, memo3);
        
        return paths1 * paths2 * paths3;
    }
    
    private Map<String, List<String>> parseGraph() {
        Map<String, List<String>> graph = new HashMap<>();
        dayStream().forEach(line -> {
            String[] parts = line.split(": ");
            String from = parts[0];
            List<String> to = parts.length > 1 ? Arrays.asList(parts[1].split(" ")) : Collections.emptyList();
            graph.put(from, to);
        });
        return graph;
    }
    
    private long countPaths(Map<String, List<String>> graph, String current, String target, Map<String, Long> memo) {
        if (current.equals(target)) {
            return 1;
        }
        
        if (memo.containsKey(current)) {
            return memo.get(current);
        }
        
        List<String> neighbors = graph.getOrDefault(current, Collections.emptyList());
        long count = 0;
        for (String neighbor : neighbors) {
            count += countPaths(graph, neighbor, target, memo);
        }
        
        memo.put(current, count);
        return count;
    }
}

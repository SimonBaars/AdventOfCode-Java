package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22 extends Day2016 {
    private static final Pattern NODE_PATTERN = Pattern.compile("/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+\\d+%");

    public Day22() {
        super(22);
    }

    public static void main(String[] args) {
        new Day22().printParts();
    }

    private record Node(int x, int y, int size, int used, int avail) {
        public boolean isViablePair(Node other) {
            return used > 0 && this != other && used <= other.avail;
        }

        public boolean isEmpty() {
            return used == 0;
        }

        public boolean isWall() {
            return size > 100; // Nodes with size > 100T are too large to move
        }
    }

    @Override
    public Object part1() {
        List<Node> nodes = parseNodes();
        int viablePairs = 0;
        for (Node a : nodes) {
            for (Node b : nodes) {
                if (a.isViablePair(b)) {
                    viablePairs++;
                }
            }
        }
        return viablePairs;
    }

    @Override
    public Object part2() {
        List<Node> nodes = parseNodes();
        Node[][] grid = createGrid(nodes);
        int maxX = grid.length - 1;
        
        // Find empty node
        Node empty = null;
        for (Node node : nodes) {
            if (node.isEmpty()) {
                empty = node;
                break;
            }
        }

        // The solution follows a pattern:
        // 1. Move empty node to position adjacent to goal data
        // 2. Move goal data one step left
        // 3. Move empty node around goal data to its right
        // 4. Repeat steps 2-3 until goal data reaches (0,0)

        // First, count steps to move empty node to position adjacent to goal data
        int stepsToGoal = Math.abs(empty.x() - (maxX - 1)) + Math.abs(empty.y() - 0);

        // Then, for each step left the goal data needs to move:
        // - 5 steps to move empty node around goal data (right, down, left, left, up)
        // - 1 step to move goal data left
        int stepsToMoveGoal = 5 * maxX;

        return stepsToGoal + stepsToMoveGoal;
    }

    private List<Node> parseNodes() {
        List<Node> nodes = new ArrayList<>();
        dayStream().skip(2).forEach(line -> {
            Matcher matcher = NODE_PATTERN.matcher(line);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int size = Integer.parseInt(matcher.group(3));
                int used = Integer.parseInt(matcher.group(4));
                int avail = Integer.parseInt(matcher.group(5));
                nodes.add(new Node(x, y, size, used, avail));
            }
        });
        return nodes;
    }

    private Node[][] createGrid(List<Node> nodes) {
        int maxX = nodes.stream().mapToInt(Node::x).max().orElse(0) + 1;
        int maxY = nodes.stream().mapToInt(Node::y).max().orElse(0) + 1;
        Node[][] grid = new Node[maxX][maxY];
        for (Node node : nodes) {
            grid[node.x()][node.y()] = node;
        }
        return grid;
    }
}

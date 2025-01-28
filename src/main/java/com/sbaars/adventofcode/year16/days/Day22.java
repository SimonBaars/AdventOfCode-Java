package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Day2016 {

    public Day22() {
        super(22);
    }

    public static void main(String[] args) {
        new Day22().printParts();
    }

    @Override
    public Object part1() {
        List<Node> nodes = parseNodes();
        int count = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node a = nodes.get(i);
            if (a.used == 0) continue;
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) continue;
                Node b = nodes.get(j);
                if (a.used <= b.avail) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<Node> parseNodes() {
        String input = day();
        return Arrays.stream(input.split("\n"))
                .filter(line -> line.startsWith("/dev/grid/node"))
                .map(line -> {
                    String[] parts = line.trim().split("\\s+");
                    String[] nameParts = parts[0].split("-");
                    int x = Integer.parseInt(nameParts[1].substring(1));
                    int y = Integer.parseInt(nameParts[2].substring(1));
                    int size = Integer.parseInt(parts[1].replace("T", ""));
                    int used = Integer.parseInt(parts[2].replace("T", ""));
                    int avail = Integer.parseInt(parts[3].replace("T", ""));
                    return new Node(x, y, size, used, avail);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object part2() {
        List<Node> nodes = parseNodes();
        Node empty = findEmptyNode(nodes);
        if (empty == null) return "No empty node found";

        int maxX = findMaxXAtY0(nodes);
        int stepsInitial = bfs(empty, maxX - 1, nodes);

        return stepsInitial + 1 + (maxX - 1) * 5;
    }

    private Node findEmptyNode(List<Node> nodes) {
        return nodes.stream()
                .filter(n -> n.used == 0)
                .findFirst()
                .orElse(null);
    }

    private int findMaxXAtY0(List<Node> nodes) {
        return nodes.stream()
                .filter(n -> n.y == 0)
                .mapToInt(n -> n.x)
                .max()
                .orElseThrow();
    }

    private int bfs(Node empty, int targetX, List<Node> nodes) {
        int maxX = nodes.stream().mapToInt(n -> n.x).max().orElse(0);
        int maxY = nodes.stream().mapToInt(n -> n.y).max().orElse(0);
        int width = maxX + 1;
        int height = maxY + 1;

        boolean[][] passable = new boolean[width][height];
        for (Node node : nodes) {
            passable[node.x][node.y] = node.used <= empty.size;
        }

        boolean[][] visited = new boolean[width][height];
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(empty.x, empty.y, 0));
        visited[empty.x][empty.y] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Position curr = queue.poll();
            if (curr.x == targetX && curr.y == 0) {
                return curr.steps;
            }
            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny] && passable[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new Position(nx, ny, curr.steps + 1));
                }
            }
        }

        return -1;
    }

    static class Node {
        final int x, y, size, used, avail;

        public Node(int x, int y, int size, int used, int avail) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.used = used;
            this.avail = avail;
        }
    }

    static class Position {
        final int x, y, steps;

        public Position(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }
    }
}
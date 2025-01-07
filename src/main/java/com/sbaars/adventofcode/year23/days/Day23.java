package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.*;

import static com.sbaars.adventofcode.common.Direction.*;

public class Day23 extends Day2023 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  @Override
  public Object part1() {
    return solve(false);
  }

  @Override
  public Object part2() {
    return solve(true);
  }

  private static final class Node {
    private final Loc loc;
    private final Map<Node, Integer> edges;

    Node(Loc loc, Map<Node, Integer> edges) {
      this.loc = loc;
      this.edges = edges;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Node node = (Node) o;
      return Objects.equals(loc, node.loc);
    }

    @Override
    public int hashCode() {
      return Objects.hash(loc);
    }
  }

  private long solve(boolean part2) {
    var input = new InfiniteGrid(dayGrid());
    Loc start = new Loc(1, 0);
    Loc target = new Loc(input.width() - 2, input.height() - 1);
    
    // Build graph of important points (junctions)
    Map<Loc, Node> nodes = new HashMap<>();
    Node startNode = new Node(start, new HashMap<>());
    nodes.put(start, startNode);
    nodes.put(target, new Node(target, new HashMap<>()));
    
    // Find all junctions (points with more than 2 possible directions)
    for (int y = 0; y < input.height(); y++) {
      for (int x = 0; x < input.width(); x++) {
        Loc loc = new Loc(x, y);
        if (input.getChar(loc) == '#') continue;
        int exits = countExits(loc, input, part2);
        if (exits > 2 || isSlope(input.getChar(loc))) {
          nodes.putIfAbsent(loc, new Node(loc, new HashMap<>()));
        }
      }
    }

    // Connect nodes
    for (Node node : nodes.values()) {
      findConnections(node, nodes, input, part2);
    }

    // DFS to find longest path
    Set<Loc> visited = new HashSet<>();
    visited.add(start);
    int result = dfs(startNode, nodes.get(target), visited, nodes);
    return result == Integer.MIN_VALUE ? 0 : result;
  }

  private boolean isSlope(char c) {
    return c == '>' || c == '<' || c == '^' || c == 'v';
  }

  private int countExits(Loc loc, InfiniteGrid grid, boolean part2) {
    return (int) four()
        .filter(d -> {
          Loc next = d.move(loc);
          char currentChar = grid.getChar(loc);
          char nextChar = grid.getChar(next);
          return (part2 || isValidMove(d, currentChar)) && 
                 nextChar != '#' && nextChar != 0;
        })
        .count();
  }

  private boolean isValidMove(Direction d, char c) {
    return switch (c) {
      case '>' -> d == EAST;
      case '<' -> d == WEST;
      case '^' -> d == NORTH;
      case 'v' -> d == SOUTH;
      default -> true;
    };
  }

  private void findConnections(Node start, Map<Loc, Node> nodes, InfiniteGrid grid, boolean part2) {
    for (Direction d : Direction.values()) {
      if (d == NORTHEAST || d == NORTHWEST || d == SOUTHEAST || d == SOUTHWEST) continue;
      
      Loc currentLoc = d.move(start.loc);
      char startChar = grid.getChar(start.loc);
      char currentChar = grid.getChar(currentLoc);
      
      if (currentChar == '#' || currentChar == 0 || 
          (!part2 && !isValidMove(d, startChar))) continue;

      Set<Loc> visited = new HashSet<>();
      visited.add(start.loc);
      visited.add(currentLoc);
      int distance = 1;
      
      while (!nodes.containsKey(currentLoc)) {
        final Loc checkLoc = currentLoc;
        var nextDirs = four()
            .filter(dir -> {
              Loc n = dir.move(checkLoc);
              char checkChar = grid.getChar(checkLoc);
              char nextChar = grid.getChar(n);
              return !visited.contains(n) && 
                     (part2 || isValidMove(dir, checkChar)) && 
                     nextChar != '#' && nextChar != 0;
            })
            .toList();
            
        if (nextDirs.isEmpty()) return;
        if (nextDirs.size() > 1) break;
        
        currentLoc = nextDirs.get(0).move(currentLoc);
        visited.add(currentLoc);
        distance++;
      }
      
      if (nodes.containsKey(currentLoc)) {
        start.edges.put(nodes.get(currentLoc), distance);
        if (part2) {
          nodes.get(currentLoc).edges.put(start, distance);
        }
      }
    }
  }

  private int dfs(Node current, Node target, Set<Loc> visited, Map<Loc, Node> nodes) {
    if (current == target) return 0;
    
    int maxLength = Integer.MIN_VALUE;
    for (var entry : current.edges.entrySet()) {
      Node next = entry.getKey();
      if (visited.contains(next.loc)) continue;
      
      visited.add(next.loc);
      int length = dfs(next, target, visited, nodes);
      if (length != Integer.MIN_VALUE) {
        maxLength = Math.max(maxLength, length + entry.getValue());
      }
      visited.remove(next.loc);
    }
    
    return maxLength;
  }
}

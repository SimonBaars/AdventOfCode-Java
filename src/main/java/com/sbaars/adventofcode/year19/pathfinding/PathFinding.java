package com.sbaars.adventofcode.year19.pathfinding;

import java.util.*;

/**
 * Helper class containing pathfinding algorithms.
 *
 * @author Ben Ruijl
 */
public class PathFinding {

  /**
   * A Star pathfinding. Note that the heuristic has to be monotonic:
   * {@code h(x) <=
   * d(x, y) + h(y)}.
   *
   * @param start Starting node
   * @param goal  Goal node
   * @return Shortest path from start to goal, or null if none found
   */
  public static <T extends Node<T>> List<T> doAStar(T start, T goal) {
    Set<T> closed = new HashSet<T>();
    Map<T, T> fromMap = new HashMap<T, T>();
    List<T> route = new LinkedList<T>();
    Map<T, Double> gScore = new HashMap<T, Double>();
    final Map<T, Double> fScore = new HashMap<T, Double>();
    PriorityQueue<T> open = new PriorityQueue<T>(11, new Comparator<T>() {

      public int compare(T nodeA, T nodeB) {
        return Double.compare(fScore.get(nodeA), fScore.get(nodeB));
      }
    });

    gScore.put(start, 0.0);
    fScore.put(start, start.getHeuristic(goal));
    open.offer(start);

    while (!open.isEmpty()) {
      T current = open.poll();
      if (current.equals(goal)) {
        while (current != null) {
          route.add(0, current);
          current = fromMap.get(current);
        }

        return route;
      }

      closed.add(current);

      for (T neighbour : current.getNeighbours()) {
        if (closed.contains(neighbour)) {
          continue;
        }

        double tentG = gScore.get(current)
            + current.getTraversalCost(neighbour);

        boolean contains = open.contains(neighbour);
        if (!contains || tentG < gScore.get(neighbour)) {
          gScore.put(neighbour, tentG);
          fScore.put(neighbour, tentG + neighbour.getHeuristic(goal));

          if (contains) {
            open.remove(neighbour);
          }

          open.offer(neighbour);
          fromMap.put(neighbour, current);
        }
      }
    }

    return new ArrayList<>();
  }

}

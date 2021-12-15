package com.sbaars.adventofcode.year21.days;

import com.sbaars.adventofcode.common.grid.NumGrid;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Day15 extends Day2021 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
//    new Day15().submitPart1();
//    new Day15().submitPart2();
  }

  @Override
  public Object part1() {
    var in = new NumGrid(day(), "\n", "");
    return shortestPath(in, in.sizeX()-1, in.sizeY()-1) - 1;
  }

  static class Cell
  {
    int x;
    int y;
    long distance;

    Cell(int x, int y, long distance)
    {
      this.x = x;
      this.y = y;
      this.distance = distance;
    }
  }

  // Custom comparator for inserting cells
// into Priority Queue
  static class distanceComparator
      implements Comparator<Cell>
  {
    public int compare(Cell a, Cell b)
    {
      if (a.distance < b.distance)
      {
        return -1;
      }
      else if (a.distance > b.distance)
      {
        return 1;
      }
      else {return 0;}
    }
  }

  // Method to return shortest path from
// top-corner to bottom-corner in 2D grid
  static long shortestPath(NumGrid g, int row,
                          int col)
  {
    long[][] grid = g.grid;
    long[][] dist = new long[row][col];
    int[] dx = { 1, 0 };
    int[] dy = { 0, 1 };

    // Initializing distance array by INT_MAX
    for(int i = 0; i < row; i++)
    {
      for(int j = 0; j < col; j++)
      {
        dist[i][j] = Integer.MAX_VALUE;
      }
    }

    // Initialized source distance as
    // initial grid position value
    dist[0][0] = grid[0][0];

    PriorityQueue<Cell> pq = new PriorityQueue<Cell>(
        row * col, new distanceComparator());

    // Insert source cell to priority queue
    pq.add(new Cell(0, 0, dist[0][0]));
    while (!pq.isEmpty())
    {
      Cell curr = pq.poll();
      for(int i = 0; i < dx.length; i++)
      {
        int rows = curr.x + dx[i];
        int cols = curr.y + dy[i];

        if (isInsideGrid(rows, cols, dist.length, dist[0].length))
        {
          if (dist[rows][cols] > dist[curr.x][curr.y] + grid[rows][cols])
          {

            // If Cell is already been reached once,
            // remove it from priority queue
            if (dist[rows][cols] != Integer.MAX_VALUE)
            {
              Cell adj = new Cell(rows, cols,
                  dist[rows][cols]);

              pq.remove(adj);
            }

            // Insert cell with updated distance
            dist[rows][cols] = dist[curr.x][curr.y] +
                grid[rows][cols];

            pq.add(new Cell(rows, cols,
                dist[rows][cols]));
          }
        }
      }
    }
    return dist[row - 1][col - 1];
  }

//
//  static long min(long x, long y, long z)
//  {
//    if (x < y)
//      return (x < z) ? x : z;
//    else
//      return (y < z) ? y : z;
//  }

//  /* Returns cost of minimum cost path
//  from (0,0) to (m, n) in mat[R][C]*/
//  static long minCost(long cost[][], int m,
//                     int n)
//  {
//    Map<Point, Integer>
//    queue.clear();
//    if (n < 0 || m < 0)
//      return Integer.MAX_VALUE;
//    else if (m == 0 && n == 0)
//      return cost[m][n];
//    else
//      return cost[m][n] +
//          Math.min(
//              minCost(cost, m-1, n),
//              minCost(cost, m, n-1) );
//  }

  static boolean isInsideGrid(int i, int j, int sizex, int sizey)
  {
    return (i >= 0 && i < sizex &&
        j >= 0 && j < sizey);
  }

  @Override
  public Object part2() {
    return "";
  }
}

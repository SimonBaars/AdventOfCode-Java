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
    return shortestPath(in, in.sizeX()-1, in.sizeY()-1, 1);
  }

  record Cell (int x, int y, long distance) {}

  static long shortestPath(NumGrid g, int row, int col, int resize)
  {
    long[][] grid = g.grid;
    long[][] dist = new long[g.sizeX()*resize][g.sizeY()*resize];
    int[] dx = { 1, 0 };
    int[] dy = { 0, 1 };

    for(int i = 0; i < dist.length; i++) {
      for(int j = 0; j < dist[0].length; j++) {
        dist[i][j] = Integer.MAX_VALUE;
      }
    }

    dist[0][0] = 0;

    PriorityQueue<Cell> pq = new PriorityQueue<>(row * col, Comparator.comparing(Cell::distance));
    pq.add(new Cell(0, 0, dist[0][0]));
    while (!pq.isEmpty()) {
      Cell curr = pq.poll();
      for(int i = 0; i < dx.length; i++) {
        int rows = curr.x + dx[i];
        int cols = curr.y + dy[i];

        if (isInsideGrid(rows, cols, dist.length, dist[0].length)) {
          long gridNum = grid[rows % grid.length][cols % grid.length] + (rows/grid.length) + (cols/grid[0].length);
          if(gridNum >= 10) gridNum -= 9;

          if (dist[rows][cols] > dist[curr.x][curr.y] + gridNum) {
            if (dist[rows][cols] != Integer.MAX_VALUE) {
              Cell adj = new Cell(rows, cols, dist[rows][cols]);
              pq.remove(adj);
            }

            dist[rows][cols] = dist[curr.x][curr.y] + gridNum;

            pq.add(new Cell(rows, cols, dist[rows][cols]));
          }
        }
      }
    }
    return dist[dist.length-1][dist[0].length-1];
  }

  static boolean isInsideGrid(int i, int j, int sizex, int sizey) {
    return i >= 0 && i < sizex && j >= 0 && j < sizey;
  }

  @Override
  public Object part2() {
    var in = new NumGrid(day(), "\n", "");
    return shortestPath(in, (in.sizeX()*5)-1, (in.sizeY()*5)-1, 5);
  }
}

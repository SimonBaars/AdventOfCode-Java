package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day8 extends Day2025 {
    public Day8() {
        super(8);
    }

    public static void main(String[] args) throws IOException {
        new Day8().printParts();
    }

    record Point3D(int x, int y, int z) {}
    
    record Connection(int i, int j, double distance) implements Comparable<Connection> {
        @Override
        public int compareTo(Connection other) {
            return Double.compare(this.distance, other.distance);
        }
    }
    
    private double distance(Point3D a, Point3D b) {
        long dx = (long)a.x - b.x;
        long dy = (long)a.y - b.y;
        long dz = (long)a.z - b.z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    @Override
    public Object part1() {
        Point3D[] points = dayStream()
            .map(line -> line.split(","))
            .map(parts -> new Point3D(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim())
            ))
            .toArray(Point3D[]::new);
        
        int n = points.length;
        
        // Generate all connections with distances
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                connections.add(new Connection(i, j, distance(points[i], points[j])));
            }
        }
        
        // Sort by distance
        Collections.sort(connections);
        
        // Union-Find structure
        UnionFind uf = new UnionFind(n);
        
        // Make 1000 connections
        int connectionsMade = 0;
        for (Connection conn : connections) {
            uf.union(conn.i, conn.j);
            connectionsMade++;
            if (connectionsMade >= 1000) {
                break;
            }
        }
        
        // Find circuit sizes
        Map<Integer, Integer> circuitSizes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            circuitSizes.put(root, uf.getSize(root));
        }
        
        // Get top 3 largest circuits
        List<Integer> sizes = new ArrayList<>(circuitSizes.values());
        sizes.sort(Collections.reverseOrder());
        
        return sizes.get(0) * sizes.get(1) * sizes.get(2);
    }
    
    static class UnionFind {
        int[] parent;
        int[] size;
        
        UnionFind(int n) {
            parent = IntStream.range(0, n).toArray();
            size = new int[n];
            Arrays.fill(size, 1);
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (size[rootX] < size[rootY]) {
                    parent[rootX] = rootY;
                    size[rootY] += size[rootX];
                } else {
                    parent[rootY] = rootX;
                    size[rootX] += size[rootY];
                }
            }
        }
        
        int getSize(int x) {
            return size[x];
        }
    }

    @Override
    public Object part2() {
        Point3D[] points = dayStream()
            .map(line -> line.split(","))
            .map(parts -> new Point3D(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim())
            ))
            .toArray(Point3D[]::new);
        
        int n = points.length;
        
        // Generate all connections with distances
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                connections.add(new Connection(i, j, distance(points[i], points[j])));
            }
        }
        
        // Sort by distance
        Collections.sort(connections);
        
        // Union-Find structure
        UnionFind uf = new UnionFind(n);
        
        // Connect until all are in one circuit
        for (Connection conn : connections) {
            int root1 = uf.find(conn.i);
            int root2 = uf.find(conn.j);
            
            if (root1 != root2) {
                uf.union(conn.i, conn.j);
                
                // Check if all are now in one circuit
                if (uf.getSize(uf.find(0)) == n) {
                    // This was the last connection needed
                    return points[conn.i].x * points[conn.j].x;
                }
            }
        }
        
        return -1; // Should not reach here
    }
}

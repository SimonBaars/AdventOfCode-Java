package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.io.IOException;
import java.util.*;

public class Day9 extends Day2025 {
    public Day9() {
        super(9);
    }

    public static void main(String[] args) throws IOException {
        new Day9().printParts();
    }

    record Point(int x, int y) {}

    @Override
    public Object part1() {
        Point[] points = dayStream()
            .map(line -> line.split(","))
            .map(parts -> new Point(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim())
            ))
            .toArray(Point[]::new);
        
        long maxArea = 0;
        
        // Try all pairs of points as opposite corners
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point p1 = points[i];
                Point p2 = points[j];
                
                // Calculate area of rectangle with these as opposite corners
                // Add 1 because we're counting tiles, not distance
                long width = Math.abs((long)p2.x - p1.x) + 1;
                long height = Math.abs((long)p2.y - p1.y) + 1;
                long area = width * height;
                
                maxArea = Math.max(maxArea, area);
            }
        }
        
        return maxArea;
    }

    @Override
    public Object part2() {
        Point[] points = dayStream()
            .map(line -> line.split(","))
            .map(parts -> new Point(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim())
            ))
            .toArray(Point[]::new);
        
        long maxArea = 0;
        
        // Try all pairs of red points as opposite corners  
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point p1 = points[i];
                Point p2 = points[j];
                
                int minX = Math.min(p1.x, p2.x);
                int maxX = Math.max(p1.x, p2.x);
                int minY = Math.min(p1.y, p2.y);
                int maxY = Math.max(p1.y, p2.y);
                
                long width = (long)(maxX - minX + 1);
                long height = (long)(maxY - minY + 1);
                long area = width * height;
                
                if (area <= maxArea) continue;
                
                boolean valid = true;
                
                // Sample perimeter with reasonable density
                int step = Math.max(10, (int)Math.max(width, height) / 80);
                
                // Top and bottom edges
                for (int x = minX; x <= maxX && valid; x += step) {
                    if (!isInsideOrOnBoundary(new Point(x, minY), points)) valid = false;
                    if (valid && !isInsideOrOnBoundary(new Point(x, maxY), points)) valid = false;
                }
                // Check last point on these edges
                if (valid && !isInsideOrOnBoundary(new Point(maxX, minY), points)) valid = false;
                if (valid && !isInsideOrOnBoundary(new Point(maxX, maxY), points)) valid = false;
                
                // Left and right edges
                for (int y = minY + step; y < maxY && valid; y += step) {
                    if (!isInsideOrOnBoundary(new Point(minX, y), points)) valid = false;
                    if (valid && !isInsideOrOnBoundary(new Point(maxX, y), points)) valid = false;
                }
                
                // Sample a few internal rows and columns
                if (valid) {
                    for (int k = 1; k <= 3 && valid; k++) {
                        int x = minX + k * (int)width / 4;
                        int y = minY + k * (int)height / 4;
                        if (x <= maxX) {
                            for (int ty = minY; ty <= maxY && valid; ty += step) {
                                if (!isInsideOrOnBoundary(new Point(x, ty), points)) valid = false;
                            }
                        }
                        if (y <= maxY) {
                            for (int tx = minX; tx <= maxX && valid; tx += step) {
                                if (!isInsideOrOnBoundary(new Point(tx, y), points)) valid = false;
                            }
                        }
                    }
                }
                
                if (valid) {
                    maxArea = area;
                }
            }
        }
        
        return maxArea;
    }
    
    private boolean isInsideOrOnBoundary(Point test, Point[] polygon) {
        // First check if it's on the boundary
        for (int i = 0; i < polygon.length; i++) {
            Point p1 = polygon[i];
            Point p2 = polygon[(i + 1) % polygon.length];
            
            if (p1.x == p2.x && p1.x == test.x) {
                int minY = Math.min(p1.y, p2.y);
                int maxY = Math.max(p1.y, p2.y);
                if (test.y >= minY && test.y <= maxY) {
                    return true;
                }
            } else if (p1.y == p2.y && p1.y == test.y) {
                int minX = Math.min(p1.x, p2.x);
                int maxX = Math.max(p1.x, p2.x);
                if (test.x >= minX && test.x <= maxX) {
                    return true;
                }
            }
        }
        
        // Check if inside
        return isInside(test, polygon);
    }
    
    // Ray casting algorithm to check if point is inside polygon
    private boolean isInside(Point test, Point[] polygon) {
        int count = 0;
        for (int i = 0; i < polygon.length; i++) {
            Point p1 = polygon[i];
            Point p2 = polygon[(i + 1) % polygon.length];
            
            if ((p1.y <= test.y && test.y < p2.y) || (p2.y <= test.y && test.y < p1.y)) {
                // Check if the ray crosses this edge
                double xIntersect = p1.x + (double)(test.y - p1.y) / (p2.y - p1.y) * (p2.x - p1.x);
                if (test.x < xIntersect) {
                    count++;
                }
            }
        }
        return count % 2 == 1;
    }
}

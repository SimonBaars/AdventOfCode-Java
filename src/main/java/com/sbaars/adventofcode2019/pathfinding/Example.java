package com.sbaars.adventofcode2019.pathfinding;

public class Example {
	public Example() {
		double[][] map = { { 0, 1, 0, 0, 0 }, 
							{ 0, 0, 0, 1, 0 }, 
							{ 1, 1, 1, 1, 0} };
		Grid2d map2d = new Grid2d(map, false);
		System.out.println(map2d.findPath(0, 0, 4, 2));
	}

	public static void main(String[] args) {
		new Example();
	}

}

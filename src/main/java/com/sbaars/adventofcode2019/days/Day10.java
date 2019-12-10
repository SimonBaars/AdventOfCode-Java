package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day10 implements Day, DoesFileOperations {

	public static void main(String[] args) throws IOException {
		new Day10().printParts();
	}

	@Override
	public int part1() throws IOException {
		String[] mapString = Arrays.stream(readDay(10).split(System.lineSeparator())).toArray(String[]::new);
		char[][] map = new char[mapString.length][mapString[0].length()];
		List<Point> asteroids = new ArrayList<>();
		for(int i = 0; i<mapString.length; i++) {
			for(int j = 0; j<mapString[i].length(); j++) {
				map[i][j] = mapString[i].charAt(j);
				if(map[i][j] == '#') {
					asteroids.add(new Point(i, j));
				}
			}
		}
		int[] nVisible = new int[asteroids.size()];
		for(int i = 0; i<nVisible.length; i++) {
			nVisible[i] = countNVisible(asteroids, asteroids.get(i), new Point(mapString.length, mapString[0].length()));
		}
//		nVisible[0] = countNVisible(asteroids, new Point(7,4), new Point(mapString.length, mapString[0].length()));
		return Arrays.stream(nVisible).max().getAsInt();
	}
	
	private int countNVisible(List<Point> asteroids, Point asteroid, Point mapSize) {
		//System.out.println("Count visible "+asteroid);
		List<Point> visible = new ArrayList<>(asteroids);
		for(int i = 0; i<visible.size(); i++) {
			if(visible.get(i).equals(asteroid)){
				continue;
			}
			Point rel = new Point(visible.get(i).x - asteroid.x, visible.get(i).y - asteroid.y);
			int max = Math.max(Math.abs(rel.x)+1, Math.abs(rel.y)+1);
			
			if(visible.get(i).equals(new Point(4,4))) {
				System.out.println("Hi");
			}
			
			Point step = new Point(rel.x, rel.y);
			for(int j = 2; j<=max; j++) {
				if(addRel(rel.x) % j == 0 && addRel(rel.y) % j == 0) {
					step = new Point(addRel(rel.x) / j, addRel(rel.y) / j);
				}
			}
			Point rem = new Point(visible.get(i).x + step.x, visible.get(i).y+step.y);
			
			if(visible.get(i).equals(new Point(4,4))) {
				System.out.println("Hi");
			}
			
			while(rem.x<=mapSize.x && rem.y<=mapSize.y && rem.x>=0 && rem.y>=0) {
				int index = visible.indexOf(rem);
				if(index!=-1) {
					visible.remove(index);
					if(index<i)
						i--;
				}
				rem = new Point(rem.x + step.x, rem.y+step.y);
				
			}
		}
		if(visible.size() == 37) System.out.println(asteroid);
		return visible.size();
	}

	private int addRel(int x) {
		if(x<0)
			return x-1;
		else if (x == 0)
			return x;
		return x+1;
	}
	
	@Override
	public int part2() throws IOException {
		return 0;
	}
}

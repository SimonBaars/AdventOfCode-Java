package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day10 implements Day, DoesFileOperations {
	
	private final List<Point> asteroids = new ArrayList<>();
	private Point baseLocation;

	public static void main(String[] args) throws IOException {
		new Day10().printParts();
	}

	@Override
	public int part1() throws IOException {
		String[] mapString = Arrays.stream(readDay(10).split(System.lineSeparator())).toArray(String[]::new);
		for(int i = 0; i<mapString.length; i++) {
			for(int j = 0; j<mapString[i].length(); j++) {
				if(mapString[i].charAt(j) == '#') {
					asteroids.add(new Point(i, j));
				}
			}
		}
		int[] nVisible = new int[asteroids.size()];
		for(int i = 0; i<nVisible.length; i++) {
			nVisible[i] = countNVisible(asteroids.get(i), new Point(mapString.length, mapString[0].length()));
		}
		baseLocation = asteroids.get(IntStream.range(0, nVisible.length).reduce((i, j) -> nVisible[i] > nVisible[j] ? i : j).getAsInt());
		return Arrays.stream(nVisible).max().getAsInt();
	}
	
	private int countNVisible(Point asteroid, Point mapSize) {
		List<Point> visible = new ArrayList<>(asteroids);
		for(int i = 0; i<visible.size(); i++) {
			if(visible.get(i).equals(asteroid)){
				continue;
			}
			Point rel = new Point(visible.get(i).x - asteroid.x, visible.get(i).y - asteroid.y);
			int max = Math.max(Math.abs(rel.x)+1, Math.abs(rel.y)+1);
			
			Point step = new Point(rel.x, rel.y);
			for(int j = 2; j<=max; j++) {
				if(addRel(rel.x) % j == 0 && addRel(rel.y) % j == 0) {
					step = new Point(addRel(rel.x) / j, addRel(rel.y) / j);
				}
			}
			
			Point rem = new Point(visible.get(i).x + step.x, visible.get(i).y+step.y);
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
		List<Asteroid> asteroidList = asteroids.stream().map(e -> new Asteroid(calcRotationAngleInDegrees(baseLocation, e), baseLocation.distance(e), e)).collect(Collectors.toList());
		double currentRotation = -0.0000000000000000000000000001;
		for(int destroyed = 0; destroyed<200; destroyed++) {
			int nextRotation = ro
		}
		return 0;
	}
	
	public static double calcRotationAngleInDegrees(Point centerPt, Point targetPt)
	{
	    double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);
	    theta += Math.PI/2.0;
	    
	    double angle = Math.toDegrees(theta);
	    if (angle < 0) {
	        angle += 360;
	    }

	    return angle;
	}
	
	class Asteroid {
		double rotation;
		double distance;
		Point position;
		
		
		public Asteroid(double rotation, double distance, Point position) {
			super();
			this.rotation = rotation;
			this.distance = distance;
			this.position = position;
		}
		
		
	}
}

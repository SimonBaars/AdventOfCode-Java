package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
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
					asteroids.add(new Point(j, i));
				}
			}
		}
		long[] nVisible = new long[asteroids.size()];
		for(int i = 0; i<nVisible.length; i++) {
			nVisible[i] = countNVisible(asteroids.get(i), new Point(mapString.length, mapString[0].length()));
		}
		baseLocation = asteroids.get(IntStream.range(0, nVisible.length).reduce((i, j) -> nVisible[i] > nVisible[j] ? i : j).getAsInt());
		return Math.toIntExact(Arrays.stream(nVisible).max().getAsLong());
	}
	
	private long countNVisible(Point asteroid, Point mapSize) {
		return asteroids.stream().map(e -> new Asteroid(calcRotationAngleInDegrees(asteroid, e), asteroid.distance(e), e)).mapToDouble(e -> e.rotation).distinct().count();
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
		System.out.println("Base = "+baseLocation);
		List<Asteroid> asteroidList = asteroids.stream().map(e -> new Asteroid(calcRotationAngleInDegrees(baseLocation, e), baseLocation.distance(e), e)).collect(Collectors.toList());
		asteroidList.remove(new Asteroid(0, 0, baseLocation));
		Asteroid prevDestroyed = new Asteroid(Double.MIN_VALUE, 0, new Point(0,0));
		for(int destroyed = 0; destroyed<250; destroyed++) {
			Asteroid prev = prevDestroyed;
			OptionalDouble nextRot = asteroidList.stream().mapToDouble(e -> e.rotation).filter(e -> e > prev.rotation).min();
			if(nextRot.isPresent()) {
				double nextRotation = nextRot.getAsDouble();
				Asteroid a = asteroidList.stream().filter(e -> e.rotation == nextRotation).reduce((a1, a2) -> a1.distance < a2.distance ? a1 : a2).get();
				asteroidList.remove(a);
				prevDestroyed = a;
				System.out.println(destroyed+". BOOM "+a.position+" rot "+a.rotation+" dis "+a.distance);
			} else {
				prevDestroyed = new Asteroid(Double.MIN_VALUE, 0, new Point(0,0));
				destroyed--;
			}
		}
		return prevDestroyed.position.x*100+prevDestroyed.position.y;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((position == null) ? 0 : position.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Asteroid))
				return false;
			Asteroid other = (Asteroid) obj;
			return position.equals(other.position);
		}
	}
}

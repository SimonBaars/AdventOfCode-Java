package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day10 implements Day, DoesFileOperations {
	
	private final List<Point> asteroids;
	private Point baseLocation;
	
	public Day10() throws IOException {
		String[] mapString = Arrays.stream(readDay(10).split(System.lineSeparator())).toArray(String[]::new);
		this.asteroids = IntStream.range(0, mapString.length).boxed().flatMap(i -> IntStream.range(0, mapString[i].length()).mapToObj(j -> new Point(j, i))).filter(p -> mapString[p.y].charAt(p.x) == '#').collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		new Day10().printParts();
	}

	@Override
	public int part1() throws IOException {
		long[] nVisible = new long[asteroids.size()];
		for(int i = 0; i<nVisible.length; i++) nVisible[i] = countNVisible(asteroids.get(i));
		baseLocation = asteroids.get(IntStream.range(0, nVisible.length).reduce((i, j) -> nVisible[i] > nVisible[j] ? i : j).getAsInt());
		return Math.toIntExact(Arrays.stream(nVisible).max().getAsLong());
	}
	
	private long countNVisible(Point asteroid) {
		return asteroids.stream().map(e -> new Asteroid(calcRotationAngleInDegrees(asteroid, e), asteroid.distance(e), e)).mapToDouble(e -> e.rotation).distinct().count();
	}
	
	@Override
	public int part2() throws IOException {
		List<Asteroid> asteroidList = asteroids.stream().map(e -> new Asteroid(calcRotationAngleInDegrees(baseLocation, e), baseLocation.distance(e), e)).collect(Collectors.toList());
		Asteroid prevDestroyed = new Asteroid(Double.MIN_VALUE, 0, new Point(0,0));
		for(int destroyed = 1; destroyed<200; destroyed++) {
			Asteroid prev = prevDestroyed;
			OptionalDouble nextRot = asteroidList.stream().mapToDouble(e -> e.rotation).filter(e -> e > prev.rotation).min();
			if(nextRot.isPresent()) {
				double nextRotation = nextRot.getAsDouble();
				Asteroid a = asteroidList.stream().filter(e -> e.rotation == nextRotation).reduce((a1, a2) -> a1.distance < a2.distance ? a1 : a2).get();
				asteroidList.remove(a);
				prevDestroyed = a;
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

package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;

import lombok.EqualsAndHashCode;

public class Day10 implements Day {
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
	public Object part1() throws IOException {
		long[] nVisible = new long[asteroids.size()];
		for(int i = 0; i<nVisible.length; i++) nVisible[i] = countNVisible(asteroids.get(i));
		baseLocation = asteroids.get(IntStream.range(0, nVisible.length).reduce((i, j) -> nVisible[i] > nVisible[j] ? i : j).getAsInt());
		return Arrays.stream(nVisible).max().getAsLong();
	}

	@Override
	public Object part2() throws IOException {
		List<Asteroid> asteroidList = asteroids.stream().map(e -> new Asteroid(baseLocation, e)).collect(Collectors.toList());
		Asteroid prevDestroyed = new Asteroid();
		for(int destroyed = 1; destroyed<200; destroyed++) {
			Asteroid prev = prevDestroyed;
			OptionalDouble nextRot = asteroidList.stream().mapToDouble(e -> e.rotation).filter(e -> e > prev.rotation).min();
			if(nextRot.isPresent()) {
				double nextRotation = nextRot.getAsDouble();
				prevDestroyed = asteroidList.stream().filter(e -> e.rotation == nextRotation).reduce((a1, a2) -> a1.distance < a2.distance ? a1 : a2).get();
				asteroidList.remove(prevDestroyed);
			}
		}
		return prevDestroyed.position.x*100+prevDestroyed.position.y;
	}

	private long countNVisible(Point asteroid) {
		return asteroids.stream().map(e -> new Asteroid(asteroid, e)).mapToDouble(e -> e.rotation).distinct().count();
	}

	@EqualsAndHashCode class Asteroid {
		@EqualsAndHashCode.Exclude double rotation;
		@EqualsAndHashCode.Exclude double distance;
		Point position;

		public Asteroid(Point center, Point me) {
			this.rotation = calcRotationAngleInDegrees(center, me);
			this.distance = me.distance(center);
			this.position = me;
		}

		public Asteroid() {
			this.rotation = Double.MIN_VALUE;
		}

		private double calcRotationAngleInDegrees(Point centerPt, Point targetPt) {
			double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x) + Math.PI/2.0;
			double angle = Math.toDegrees(theta);
			return angle < 0 ? angle + 360 : angle;
		}
	}
}

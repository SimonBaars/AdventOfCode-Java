package com.sbaars.adventofcode.year19.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode.common.Day;

import com.sbaars.adventofcode.year19.Day2019;
import lombok.EqualsAndHashCode;

public class Day10 extends Day2019 {

	private final List<Point> asteroids;
	private Point baseLocation;

	public Day10()  {
		super(10);
		String[] mapString = dayStrings();
		this.asteroids = IntStream.range(0, mapString.length).boxed().flatMap(i -> IntStream.range(0, mapString[i].length()).mapToObj(j -> new Point(j, i))).filter(p -> mapString[p.y].charAt(p.x) == '#').collect(Collectors.toList());
	}

	public static void main(String[] args)  {
		new Day10().printParts();
	}

	@Override
	public Object part1()  {
		long[] nVisible = new long[asteroids.size()];
		for(int i = 0; i<nVisible.length; i++) nVisible[i] = countNVisible(asteroids.get(i));
		baseLocation = asteroids.get(IntStream.range(0, nVisible.length).reduce((i, j) -> nVisible[i] > nVisible[j] ? i : j).getAsInt());
		return Arrays.stream(nVisible).max().getAsLong();
	}

	@Override
	public Object part2()  {
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

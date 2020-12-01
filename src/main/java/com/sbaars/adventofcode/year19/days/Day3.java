package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.Direction;
import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.sbaars.adventofcode.year19.Day2019;
import lombok.EqualsAndHashCode;

public class Day3 extends Day2019 {

	private Set<Step> intersect;

	public Day3() throws IOException {
		super(3);
		String[] strings = dayStrings();
		Walk[] walks1 = mapToWalks(strings[0]), walks2 = mapToWalks(strings[1]);
		Set<Step> walkedLocations = new HashSet<>();
		calculateDistance(walks1, walkedLocations, false);
		this.intersect = calculateDistance(walks2, walkedLocations, true);
	}

	public static void main(String[] args) throws IOException {
		new Day3().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return intersect.stream().mapToInt(e -> distance(e.point)).min().orElse(Integer.MAX_VALUE);
	}

	@Override
	public Object part2() throws IOException {
		return intersect.stream().mapToInt(e -> e.steps).min().orElse(Integer.MAX_VALUE);
	}

	private Set<Step> calculateDistance(Walk[] walks1, Set<Step> walkedLocations, boolean collect) {
		Set<Step> intersectingLocations = new HashSet<>();
		int x = 0, y = 0, steps = 0;
		for(Walk walk : walks1) {
			for(;walk.distance>0;walk.distance--) {
				switch(walk.dir) {
				case NORTH: y++; break;
				case SOUTH: y--; break;
				case WEST: x--; break;
				case EAST: x++; break;
				}
				performStep(walkedLocations, collect, intersectingLocations, x, y, steps);
				steps++;
			}
		}
		return intersectingLocations;
	}

	private void performStep(Set<Step> walkedLocations, boolean collect, Set<Step> intersectingLocations, int x, int y, int steps) {
		Step currentStep = new Step(new Point(x,y), steps);
		if(collect) {
			if(walkedLocations.contains(currentStep) && !intersectingLocations.contains(currentStep)) {
				Step step = walkedLocations.stream().filter(e -> e.equals(currentStep)).findAny().get();
				intersectingLocations.add(step);
				step.combine(currentStep);
			}
		} else {
			walkedLocations.add(currentStep);
		}
	}

	public int distance(Point p) {
		return Math.abs(p.x) + Math.abs(p.y);
	}

	private Walk[] mapToWalks(String string) {
		return Arrays.stream(string.split(",")).map(Walk::new).toArray(Walk[]::new);
	}

	class Walk {
		private final Direction dir;
		private int distance;

		public Walk(String code) {
			this.dir = Direction.getByDirCode(code.charAt(0));
			this.distance = Integer.parseInt(code.substring(1));
		}
	}

	@EqualsAndHashCode class Step {
		private final Point point;
		@EqualsAndHashCode.Exclude private int steps;
		@EqualsAndHashCode.Exclude private boolean isCombined = false;

		public Step(Point point, int steps) {
			this.point = point;
			this.steps = steps + 1;
		}

		public void combine(Step step) {
			if(!isCombined) {
				steps+=step.steps;
				isCombined = true;
			}
		}
	}
}

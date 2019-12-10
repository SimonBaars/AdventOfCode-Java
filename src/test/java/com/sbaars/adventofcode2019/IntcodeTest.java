package com.sbaars.adventofcode2019;

import java.io.IOException;

import com.sbaars.adventofcode2019.days.Day2;
import com.sbaars.adventofcode2019.days.Day5;
import com.sbaars.adventofcode2019.days.Day7;
import com.sbaars.adventofcode2019.days.Day9;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IntcodeTest extends TestCase {
	public void testDay2() throws IOException {
		Assert.assertEquals(new Day2().part1(), 8017076);
		Assert.assertEquals(new Day2().part2(), 3146);
	}
	
	public void testDay5() throws IOException {
		Assert.assertEquals(new Day5().part1(), 11049715);
		Assert.assertEquals(new Day5().part2(), 2140710);
	}
	
	public void testDay7() throws IOException {
		Assert.assertEquals(new Day7().part1(), 116680);
		Assert.assertEquals(new Day7().part2(), 89603079);
	}
	
	public void testDay9() throws IOException {
		Assert.assertEquals(new Day9().part1(), 518058886);
		Assert.assertEquals(new Day9().part2(), 44292);
	}
}

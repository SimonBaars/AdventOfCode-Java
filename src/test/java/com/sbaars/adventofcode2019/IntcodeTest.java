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
		Assert.assertEquals(8017076L, new Day2().part1());
		Assert.assertEquals(3146, new Day2().part2());
	}
	
	public void testDay5() throws IOException {
		Assert.assertEquals(11049715L, new Day5().part1());
		Assert.assertEquals(2140710L, new Day5().part2());
	}
	
	public void testDay7() throws IOException {
		Assert.assertEquals(116680, new Day7().part1());
		Assert.assertEquals(89603079, new Day7().part2());
	}
	
	public void testDay9() throws IOException {
		Assert.assertEquals(2518058886L, new Day9().part1());
		Assert.assertEquals(44292L, new Day9().part2());
	}
}

package com.sbaars.adventofcode2019.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.ComputerThread;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day23 implements Day {

	public static void main(String[] args) throws IOException {
		System.out.println(new Day23().part2());
	}

	@Override
	public Object part1() throws IOException {
		ComputerThread[] ic = IntStream.range(0, 50).mapToObj(i -> {
			try {
				return new ComputerThread(new IntcodeComputer(23, i));
			} catch (IOException e) {
				return null;
			}
		}).toArray(ComputerThread[]::new);
		
		
		while(true) {
			for(int i = 0; i<ic.length; i++) {
				while(ic[i].output.size()>2) {
					int pc = Math.toIntExact(ic[i].output.poll());
					long x = ic[i].output.poll();
					long y = ic[i].output.poll();
					if(pc == 255) {
						//System.out.println(x+", "+y+", "+pc);
						//ic[pc].ic.addInput(x);
						//ic[pc].ic.addInput(y);
						//System.out.println(new IntcodeComputer(23, 255).run());
						//Arrays.stream(ic).forEach(e -> e.stop());
						return y;
					}
					ic[pc].ic.addInput(x);
					ic[pc].ic.addInput(y);
				}
				/*while((pc = Math.toIntExact(ic[i].run()))!=-1) {
					x = ic[i].run();
					y = ic[i].run();
					System.out.println(x+", "+y+", "+pc);
					if(pc == 255) return y;
					ic[pc].addInput(x);
					ic[pc].addInput(y);
				}*/
			}
			
			//x = ic[pc].run();
			//y = ic[pc].run();
			//pc = Math.toIntExact(ic[pc].run());
		}
	}
	
	@Override
	public Object part2() throws IOException {
		ComputerThread[] ic = IntStream.range(0, 50).mapToObj(i -> {
			try {
				return new ComputerThread(new IntcodeComputer(23, i));
			} catch (IOException e) {
				return null;
			}
		}).toArray(ComputerThread[]::new);
		
		IntcodeComputer nat = new IntcodeComputer(23, 255);
		
		long prev = -1;
		while(true) {
			for(int i = 0; i<ic.length; i++) {
				while(ic[i].output.size()>2) {
					int pc = Math.toIntExact(ic[i].output.poll());
					long x = ic[i].output.poll();
					long y = ic[i].output.poll();
					if(pc == 255) {
						nat.addInput(x);
						nat.addInput(y);
						pc = Math.toIntExact(nat.run());
						x = nat.run();
						y = nat.run();
						System.out.println(x+", "+y+", "+pc);
						if(y == prev)
							return y;
						prev = y;
						//ic[pc].ic.addInput(x);
						//ic[pc].ic.addInput(y);
						//System.out.println(new IntcodeComputer(23, 255).run());
						//Arrays.stream(ic).forEach(e -> e.stop());
						//return y;
					}
					ic[pc].ic.addInput(x);
					ic[pc].ic.addInput(y);
				}
				/*while((pc = Math.toIntExact(ic[i].run()))!=-1) {
					x = ic[i].run();
					y = ic[i].run();
					System.out.println(x+", "+y+", "+pc);
					if(pc == 255) return y;
					ic[pc].addInput(x);
					ic[pc].addInput(y);
				}*/
			}
			
			//x = ic[pc].run();
			//y = ic[pc].run();
			//pc = Math.toIntExact(ic[pc].run());
		}
	}
}

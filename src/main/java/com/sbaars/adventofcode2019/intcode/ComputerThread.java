package com.sbaars.adventofcode2019.intcode;

import java.util.ArrayDeque;
import java.util.Queue;

public class ComputerThread extends Thread {
	public IntcodeComputer ic;
	public final Queue<Long> output = new ArrayDeque<>();
	
	public ComputerThread(IntcodeComputer ic) {
		super();
		this.ic = ic;
		start();
	}

	@Override
	public void run() {
		while(true) {
			long res = ic.run();
			if(res!=-1)
				output.add(res);
		}
	}
}

package com.sbaars.adventofcode.year18;

import java.awt.Point;
import java.util.*;

public class Day13 extends Day2018 {
	//Minecart madness
	
	public static void main(String[] args) {
		
		new Day13().part1();

	}

	public Day13() {
		super(13);
	}

	boolean part1 = false;
	
	public Object part1() {
		//*  Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = dayStrings();

		
		char[][] tracks = new char[input.length][input[0].length()];
		
		HashMap<Point,Cart> carts = new HashMap<Point,Cart>();
		for(int y=0;y<input.length;y++) {
			for(int x=0;x<input[0].length();x++) {
				tracks[y][x] = input[y].charAt(x);
				if(tracks[y][x] == '^' || tracks[y][x] == 'v' || tracks[y][x] == '<' || tracks[y][x] == '>') {
					Cart c = new Cart(tracks[y][x], x, y);
					carts.put(c.p, c);
				}
			}
		}
		
		
		//move until collision
		boolean ok = true;
		int count = 0;
		while(ok) {
			
			if(count % 100 == 0) {
				System.out.println("Step "+count+"   carts "+carts.size());
			}
			
			
			for(int y=0;y<tracks.length&&ok;y++) {
				for(int x=0;x<tracks[0].length&&ok;x++) {
					
					if(tracks[y][x] == '^' || tracks[y][x] == 'v' || tracks[y][x] == '<' || tracks[y][x] == '>') {
						//move cart
						Point loc = new Point(x,y);
						Cart c = carts.get(loc);
						carts.remove(loc);
						if(c==null) {
							System.out.println("ERROR!");
						}else {
							c.move(tracks);
						}
						//put cart in new location
						Cart hit = carts.get(c.p);
						if(hit!=null) {
							if(part1) {
								System.out.println("Collision at "+c.p.x+","+c.p.y+"   during step "+count);
								ok = false;
							}else {
								//remove hit cart, and fix track (if this hit cart hasn't moved yet)
								carts.remove(c.p);
								System.out.println(count+", "+c.p);
								tracks[hit.p.y][hit.p.x] = hit.track;
							}
						}else {
							carts.put(c.p, c);
						}
					}
				}
			}
			
			//draw cart in new location
			for(Cart c:carts.values()) {
				c.moveTwo(tracks);
			}
			
			
			count++;
			//print track
			/*
			System.out.println(count);
			for(int y=0;y<input.length&&ok;y++) {
				for(int x=0;x<input[0].length()&&ok;x++) {
					System.out.print(tracks[y][x]);
				}
				System.out.println();
			}
			System.out.println("\n");
			*/
			
			if(!part1 && carts.size() == 1) {
				System.out.println("Last cart is "+carts.values().iterator().next().toString()+"  at step "+count);
				ok = false;
			}
			
		}

		return 0;

	}

	@Override
	public Object part2() {
		return null;
	}

	enum Dir {
		NORTH, EAST, SOUTH, WEST;
		
		public Dir turnRight() {
			return Dir.values()[(this.ordinal() + 1 )% Dir.values().length];
		}
		
		public Dir turnLeft() {
			return Dir.values()[(this.ordinal() + 3 )% Dir.values().length];
		}
		
		public Point move(Point p) {
			switch(this) {
				case NORTH:
					p.y--;
					break;
				case SOUTH:
					p.y++;
					break;
				case EAST:
					p.x++;
					break;
				case WEST:
					p.x--;
					break;
			}
			return p;
		}
		
		public char getCart() {
			switch(this) {
				case NORTH:
					return '^';
				case SOUTH:
					return 'v';
				case EAST:
					return '>';
				case WEST:
					return '<';
			}
			return '#';//should never be hit
		}
	}
	
	enum Turn {
		LEFT, STRAIGHT, RIGHT;
		
		public Dir getNewDir(Dir dir) {
			if(this == Turn.STRAIGHT) {
				return dir;
			}else if(this == Turn.LEFT) {
				return dir.turnLeft();
			}else {
				return dir.turnRight();
			}
		}
		
		public Turn nextTurn() {
			return Turn.values()[(this.ordinal() + 1)% Turn.values().length];
		}
	}
	
	
	class Cart{
		Point p;
		Dir dir;
		Turn turn;
		char track;
		
		public String toString() {
			return "Cart at "+p.x+","+p.y+" facing "+dir.name()+" on "+track+" making next turn "+turn.name();
		}
		
		public Cart (char c, int x, int y) {
			p = new Point(x,y);
			turn = Turn.LEFT;
			switch(c) {
				case '^':
					dir = Dir.NORTH;
					track = '|';
					break;
				case '>':
					dir = Dir.EAST;
					track = '-';
					break;
				case 'v':
					dir = Dir.SOUTH;
					track = '|';
					break;
				case '<':
					dir = Dir.WEST;
					track = '-';
			}
		}
		
		public void move(char[][] tracks) {
			//remove cart character and put back correct track character
			tracks[p.y][p.x] = track;
			
			//move the cart
			p = dir.move(p);
			
			//get the track character for the new location
			track = tracks[p.y][p.x];
			
			//change direction if at a corner or crossing
			if(track == '/') {
				if(dir == Dir.EAST || dir == Dir.WEST) {
					dir = dir.turnLeft();
				}else {
					dir = dir.turnRight();
				}
			}else if(track == '\\') {
				if(dir == Dir.EAST || dir == Dir.WEST) {
					dir = dir.turnRight();
				}else {
					dir = dir.turnLeft();
				}
			}else if(track == '+') {
				dir = turn.getNewDir(dir);
				turn = turn.nextTurn();
			}
			
			//tracks[p.y][p.x] = dir.getCart();
		}
		
		public void moveTwo(char[][] tracks) {
			//put cart character on to tracks after all carts have moved 
			//(so we don't accidentally double move (or more) this cart if going east or south)
			tracks[p.y][p.x] = dir.getCart();
		}
	}
	
}
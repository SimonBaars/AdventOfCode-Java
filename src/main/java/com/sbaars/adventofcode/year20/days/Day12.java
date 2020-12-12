package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year20.Day2020;
import lombok.Data;
import lombok.Value;

import java.awt.*;
import java.util.List;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static java.util.stream.Collectors.toList;

public class Day12 extends Day2020 {
    public static void main(String[] args) {
        new Day12().printParts();
    }

    public Day12() {
        super(12);
    }

    @Override
    public Object part1() {
        List<Flight> input = dayStream().map(e -> new Flight(e.charAt(0), Integer.parseInt(e.substring(1)))).collect(toList());
        Direction face = EAST;
        Point location = new Point(0, 0);
        for(Flight f : input){
            switch(f.dir){
                case 'L':case 'R':
                    {
                    int num = f.distance;
                    while(num>0){
                        face = face.turn(f.dir == 'R');
                        num-=90;
                    }
                    break;
                }
                case 'F': {
                    location = face.move(location, f.distance);
                    break;
                }
                default: {
                    location = Direction.getByDir(f.dir).move(location, f.distance);
                    break;
                }
            }
        }
        return Math.abs(location.x) + Math.abs(location.y);
    }

    @Data
    @Value
    class Flight {
        char dir;
        int distance;
    }

    @Override
    public Object part2() {
        List<Flight> input = dayStream().map(e -> new Flight(e.charAt(0), Integer.parseInt(e.substring(1)))).collect(toList());
        Point waypoint = new Point(10, -1);
        Point location = new Point(0, 0);
        for(Flight f : input){
            switch(f.dir){
                case 'L':case 'R':
                {
                    int num = f.distance;
                    while(num>0){
                        waypoint = turn(waypoint, f.dir == 'R');
                        num-=90;
                    }
                    break;
                }
                case 'F': {
                    location = new Point(location.x+(waypoint.x*f.distance), location.y+(waypoint.y*f.distance));
                    break;
                }
                default: {
                    waypoint = Direction.getByDir(f.dir).move(waypoint, f.distance);
                    break;
                }
            }
        }
        return Math.abs(location.x) + Math.abs(location.y);
    }

    private Point turn(Point w, boolean b) {
        return b ? new Point(-w.y, w.x) : new Point(w.y, -w.x);
    }
}

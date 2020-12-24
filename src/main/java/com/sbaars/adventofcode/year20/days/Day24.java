package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.HexDirection;
import com.sbaars.adventofcode.year20.Day2020;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class Day24 extends Day2020 {
    public static void main(String[] args) {
        new Day24().printParts();
    }

    Set<Point> visited = new HashSet<>();

    public Day24() {
        super(24);
    }

    @Override
    public Object part1() {
        var input = stream(dayStrings()).map(this::read).collect(toList());
        for(List<HexDirection> dirs : input){
            Point pos = new Point(0,0);
            for(HexDirection dir : dirs){
                pos = dir.move(pos);
            }
            if(!visited.add(pos)){
                visited.remove(pos);
            }
        }
        return visited.size();
    }

    public List<HexDirection> read(String dirs){
        List<HexDirection> res = new ArrayList<>(dirs.length());
        while(dirs.length()>0){
            Optional<HexDirection> direction;
            if(dirs.length()>1 && (direction = HexDirection.get(dirs.substring(0,2))).isPresent()){
                res.add(direction.get());
                dirs = dirs.substring(2);
            } else if ((direction = HexDirection.get(dirs.substring(0,1))).isPresent()){
                res.add(direction.get());
                dirs = dirs.substring(1);
            }
        }
        return res;
    }

    @Override
    public Object part2() {
        for(int i = 0; i<100; i++){
            Set<Point> newPos = new HashSet<>();
            visited.forEach(p -> addNeighbors(visited, newPos, new HashSet<>(), p, true));
            visited = newPos;
        }
        return visited.size();
    }

    public void addNeighbors(Set<Point> pos, Set<Point> newPos, Set<Point> checkedPos, Point p, boolean active){
        if(!checkedPos.contains(p)) {
            int neighbours = 0;
            checkedPos.add(p);
            for(HexDirection dir : HexDirection.values()) {
                Point x = dir.move(p);
                if (pos.contains(x)) {
                    neighbours++;
                } else if (active) {
                    addNeighbors(pos, newPos, checkedPos, x, false);
                }
            }
            if((active && (neighbours == 1 || neighbours == 2)) ||
                    (!active && neighbours == 2)){
                newPos.add(p);
            }
        }
    }
}

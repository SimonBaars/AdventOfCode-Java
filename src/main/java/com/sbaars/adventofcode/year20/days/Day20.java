package com.sbaars.adventofcode.year20.days;

import static com.sbaars.adventofcode.common.Direction.EAST;
import static com.sbaars.adventofcode.common.Direction.NORTH;
import static com.sbaars.adventofcode.common.Direction.SOUTH;
import static com.sbaars.adventofcode.common.Direction.WEST;
import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Streams;
import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.year20.Day2020;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Value;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Day20 extends Day2020 {
    public static void main(String[] args) {
        new Day20().printParts();
    }

    public Day20() {
        super(20);
    }

    @Override
    public Object part1() {
        Grid[] input = stream(day().split("\n\n")).map(Grid::new).toArray(Grid[]::new);
        Multimap<String, Metadata> map = ArrayListMultimap.create();
        stream(input).forEach(e -> e.getSides(map));
        Map<String, Collection<Metadata>> map2 = map.asMap();
        List<Long> answer = new ArrayList<>();
        for(Grid g : input){
            List<String> possibleSides = Streams.concat(g.findSides(false).values().stream(), g.findSides(true).values().stream()).collect(Collectors.toList());
            if(possibleSides.stream().filter(e -> map2.get(e).size() > 1).count() == 4L){
                answer.add(g.id);
            }
        }
        return answer.stream().mapToLong(e -> e).reduce((a,b) -> a * b).getAsLong();
    }

    @Data
    @Value
    public static class Grid{
        long id;
        char[][] grid;

        public Grid(String s){
            this.id = parseLong(s.substring(5,9));
            this.grid = stream(s.substring(11).split("\n")).map(String::toCharArray).toArray(char[][]::new);
        }

        public Multimap<String, Metadata> getSides(Multimap<String, Metadata> map){
            findSides(map, false);
            findSides(map, true);
            return map;
        }

        private void findSides(Multimap<String, Metadata> map, boolean flipped) {
            Point p = new Point(flipped ? grid.length-1 : 0, flipped ? 0 : grid.length-1);
            Direction[] d = Direction.fourDirections();
            if(flipped) ArrayUtils.reverse(d);
            for(Direction dir : d){
                StringBuilder res = new StringBuilder();
                for(int i = 0; i<grid.length; i++){
                    res.append(dir.getInGrid(grid, p));
                    if(i != grid.length-1)
                        p = dir.move(p);
                }
                map.put(res.toString(), new Metadata(id, dir.turn(false), flipped));
            }
        }

        private Map<Direction, String> findSides(boolean flipped) {
            Map<Direction, String> map = new HashMap<>();
            Point p = new Point(flipped ? grid.length-1 : 0, flipped ? 0 : grid.length-1);
            Direction[] d = Direction.fourDirections();
            if(flipped) ArrayUtils.reverse(d);
            for(Direction dir : d){
                StringBuilder res = new StringBuilder();
                for(int i = 0; i<grid.length; i++){
                    res.append(dir.getInGrid(grid, p));
                    if(i != grid.length-1)
                        p = dir.move(p);
                }
                map.put(dir, res.toString());
            }
            return map;
        }

        public long countWithoutSides(){
            long total = 0;
            for(int i = 1; i<grid.length-1; i++){
                for(int j = 1; j<grid[i].length-1; j++){
                    if(grid[i][j] == '#'){
                        total++;
                    }
                }
            }
            return total;
        }
    }

    @Data
    @Value
    public static class Metadata{
        long id;
        Direction dir;
        boolean flipped;

        public boolean validMetadata(List<Metadata> l){
            Map<Direction, Boolean> map = new EnumMap<Direction, Boolean>(Direction.class);
            for(Metadata m : l){
                map.put(m.dir, m.flipped);
            }
            return l.size() == 4 &&
                    map.size() == 4 &&
                    l.stream().map(n -> n.id).distinct().count() == 4L &&
                    map.get(NORTH).equals(map.get(SOUTH)) &&
                    map.get(EAST).equals(map.get(WEST)) &&
                    map.get(NORTH).equals(map.get(EAST));
        }
    }

    @Override
    public Object part2() {
        Grid[] input = stream(day().split("\n\n")).map(Grid::new).toArray(Grid[]::new);
        long total = 0;
        for(Grid g : input){
            total+=g.countWithoutSides();
        }
        long seaMonsterSize = 15;
        for(long n = 0; total-n>0; n+=seaMonsterSize){
            System.out.println((n/15)+". "+(total-n));
        }
        return total-(35*seaMonsterSize);
    }
}

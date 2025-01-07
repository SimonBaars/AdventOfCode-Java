package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.location.Loc;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day5 extends Day2023 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  public record Mapping(String source, String dest, List<SeedNumbers> nums) {
  }

  public record SeedNumbers(long destinationStart, long sourceStart, long size) {
  }

  public record Range(long start, long end) {
    public boolean overlaps(Range other) {
      return start <= other.end && end >= other.start;
    }

    public Range intersection(Range other) {
      return new Range(Math.max(start, other.start), Math.min(end, other.end));
    }
  }

  private List<Range> processRanges(List<Range> inputRanges, List<SeedNumbers> mappings) {
    List<Range> result = new ArrayList<>();
    for (Range input : inputRanges) {
      List<Range> unmapped = new ArrayList<>();
      unmapped.add(input);
      
      for (SeedNumbers mapping : mappings) {
        Range mapRange = new Range(mapping.sourceStart(), mapping.sourceStart() + mapping.size() - 1);
        List<Range> nextUnmapped = new ArrayList<>();
        
        for (Range current : unmapped) {
          if (current.overlaps(mapRange)) {
            Range overlap = current.intersection(mapRange);
            long offset = mapping.destinationStart() - mapping.sourceStart();
            result.add(new Range(overlap.start() + offset, overlap.end() + offset));
            
            if (current.start() < overlap.start()) {
              nextUnmapped.add(new Range(current.start(), overlap.start() - 1));
            }
            if (current.end() > overlap.end()) {
              nextUnmapped.add(new Range(overlap.end() + 1, current.end()));
            }
          } else {
            nextUnmapped.add(current);
          }
        }
        unmapped = nextUnmapped;
      }
      result.addAll(unmapped);
    }
    return result;
  }

  @Override
  public Object part1() {
    String[] day = day().split("\n\n");
    List<Long> seeds = getSeeds(day);
    List<Mapping> mappings = mappings(day);
    return seeds.stream().mapToLong(seed -> findLocations(day, mappings, seed)).min().getAsLong();
  }

  private ArrayList<Long> getSeeds(String[] day) {
    return readString(day[0], "seeds: %ln", " ", ArrayList.class);
  }

  private long findLocations(String[] day, List<Mapping> mappings, long seed) {
    return mappings
        .stream()
        .reduce(seed, (s, m) -> m.nums.stream().map(n -> new Loc(n.sourceStart, n.sourceStart + n.size - 1).contains(s) ? s + (n.destinationStart - n.sourceStart) : -1).filter(n -> n != -1).findAny().orElse(s), Long::sum);
  }

  private static List<Mapping> mappings(String[] day) {
    return Arrays.stream(Arrays.copyOfRange(day, 1, day.length)).map(s -> readString(s, "%s-to-%s map:\n%l(%n %n %n)", "\n", Mapping.class, SeedNumbers.class)).toList();
  }

  @Override
  public Object part2() {
    String[] day = day().split("\n\n");
    List<Long> seeds = getSeeds(day);
    List<Mapping> mappings = mappings(day);
    
    List<Range> ranges = new ArrayList<>();
    for (int i = 0; i < seeds.size(); i += 2) {
      ranges.add(new Range(seeds.get(i), seeds.get(i) + seeds.get(i + 1) - 1));
    }
    
    for (Mapping mapping : mappings) {
      ranges = processRanges(ranges, mapping.nums());
    }
    
    return ranges.stream().mapToLong(Range::start).min().getAsLong();
  }
}

package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day20 extends Day2016 {
    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+)-(\\d+)");
    private static final long MAX_IP = 4294967295L;

    public Day20() {
        super(20);
    }

    public static void main(String[] args) {
        new Day20().printParts();
    }

    private record Range(long start, long end) implements Comparable<Range> {
        @Override
        public int compareTo(Range other) {
            return Long.compare(this.start, other.start);
        }
    }

    private List<Range> mergeRanges(List<Range> ranges) {
        List<Range> merged = new ArrayList<>();
        Range current = ranges.get(0);

        for (int i = 1; i < ranges.size(); i++) {
            Range next = ranges.get(i);
            if (current.end + 1 >= next.start) {
                current = new Range(current.start, Math.max(current.end, next.end));
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);

        return merged;
    }

    private List<Range> parseRanges() {
        List<Range> ranges = new ArrayList<>();
        for (String line : dayStream().toList()) {
            Matcher m = RANGE_PATTERN.matcher(line);
            if (m.matches()) {
                ranges.add(new Range(
                    Long.parseLong(m.group(1)),
                    Long.parseLong(m.group(2))
                ));
            }
        }
        ranges.sort(null);
        return mergeRanges(ranges);
    }

    private long findLowestUnblockedIP() {
        List<Range> ranges = parseRanges();
        if (ranges.get(0).start > 0) {
            return 0;
        }
        
        for (int i = 0; i < ranges.size() - 1; i++) {
            if (ranges.get(i).end + 1 < ranges.get(i + 1).start) {
                return ranges.get(i).end + 1;
            }
        }

        return ranges.get(ranges.size() - 1).end + 1;
    }

    private long countAllowedIPs() {
        List<Range> ranges = parseRanges();
        long count = 0;
        long lastEnd = -1;

        for (Range range : ranges) {
            if (range.start > lastEnd + 1) {
                count += range.start - lastEnd - 1;
            }
            lastEnd = range.end;
        }

        if (lastEnd < MAX_IP) {
            count += MAX_IP - lastEnd;
        }

        return count;
    }

    @Override
    public Object part1() {
        return findLowestUnblockedIP();
    }

    @Override
    public Object part2() {
        return countAllowedIPs();
    }
}

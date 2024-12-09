package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.AoCUtils.zipWithIndex;
import static java.lang.Integer.parseInt;
import static java.util.Collections.reverseOrder;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day9 extends Day2024 {
  public Day9() {
    super(9);
  }

  public static void main(String[] args) {
    new Day9().printParts();
  }

  record Data(int start, int end, int length) {
    Data(int start) {
      this(start, start, 1);
    }

    Data updateEnd(int end) {
      return new Data(this.start, end, end - this.start + 1);
    }
  }

  @Override
  public Object part1() {
    var disk = disk();
    compactDisk(disk);
    return checksum(disk);
  }

  @Override
  public Object part2() {
    var disk = disk();
    var files = parseData(disk);
    moveFiles(disk, files);
    return checksum(disk);
  }

  private List<Integer> disk() {
    AtomicInteger id = new AtomicInteger(0);
    AtomicBoolean b = new AtomicBoolean(true);
    return dayStream("").filter(s -> !s.isBlank()).map(c -> parseInt(c)).flatMap(length -> {
      int value = b.getAndSet(!b.get()) ? id.getAndIncrement() : -1;
      return Stream.generate(() -> value).limit(length);
    }).collect(Collectors.toCollection(ArrayList::new));
  }

  private void compactDisk(List<Integer> disk) {
    while (disk.indexOf(-1) != -1) {
      int rightBlock;
      do {
        rightBlock = disk.remove(disk.size() - 1);
      } while (rightBlock == -1);
      disk.set(disk.indexOf(-1), rightBlock);
    }
  }

  private Map<Integer, Data> parseData(List<Integer> disk) {
    return zipWithIndex(disk.stream()).filter(b -> b.e() != -1).reduce(new HashMap<Integer, Data>(), (map, b) -> {
      map.merge(b.e(), new Data(b.i()), (d1, d2) -> d1.updateEnd(b.i()));
      return map;
    }, (a, b) -> {
      a.putAll(b);
      return a;
    });
  }

  private void moveFiles(List<Integer> disk, Map<Integer, Data> files) {
    files.keySet().stream().sorted(reverseOrder()).forEach(id -> {
      Data d = files.get(id);
      int l = d.length();
      int file = findFile(disk, l, d.start());

      if (file != -1) {
        clearOldPositions(disk, d);
        setNewPositions(disk, id, file, l);
        files.put(id, new Data(file, file + l - 1, l));
      }
    });
  }

  private int findFile(List<Integer> disk, int length, int start) {
    int count = 0, segmentStart = -1;
    for (int i = 0; i < start; i++) {
      if (disk.get(i) == -1) {
        if (segmentStart == -1) segmentStart = i;
        if (++count == length) return segmentStart;
      } else {
        count = 0;
        segmentStart = -1;
      }
    }
    return -1;
  }

  private void clearOldPositions(List<Integer> disk, Data data) {
    rangeClosed(data.start(), data.end()).forEach(pos -> disk.set(pos, -1));
  }

  private void setNewPositions(List<Integer> disk, int id, int start, int length) {
    range(start, start + length).forEach(pos -> disk.set(pos, id));
  }

  private long checksum(List<Integer> disk) {
    return zipWithIndex(disk.stream()).filter(b -> b.e() != -1).mapToLong(b -> (long) b.i() * b.e()).sum();
  }
}

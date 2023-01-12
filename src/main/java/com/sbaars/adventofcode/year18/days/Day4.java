package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.common.map.ListCountMap;
import com.sbaars.adventofcode.year18.Day2018;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sbaars.adventofcode.util.AOCUtils.findMax;
import static com.sbaars.adventofcode.util.AOCUtils.findWhere;
import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day4 extends Day2018 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  public record Event(String timestamp, String event) {
    public LocalDateTime getTimestamp() {
      return LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
  }

  @Override
  public Object part1() {
    List<Event> events = dayStream().sorted().map(s -> readString(s, "[%s] %s", Event.class)).toList();
    int currentGuard = 0;
    LocalDateTime asleep = events.get(0).getTimestamp();
    ListCountMap<Integer, Integer> lcm = new ListCountMap<>();
    for(Event event : events) {
      if(event.event.startsWith("Guard")) {
        currentGuard = readString(event.event, "Guard #%i begins shift", AtomicInteger.class).get();
      } else if (event.event.equals("falls asleep")) {
        asleep = event.getTimestamp();
      } else {
        LocalDateTime d = event.getTimestamp();
        while(asleep.isBefore(d)) {
          lcm.increment(currentGuard, asleep.getMinute());
          asleep = asleep.plus(Duration.ofMinutes(1));
        }
      }
    }
    var max = findMax(lcm.entrySet(), g -> g.getValue().sum());
    return max.getKey() * findMax(max.getValue().entrySet(), Map.Entry::getValue).getKey();
  }

  @Override
  public Object part2() {
    return "";
  }
}

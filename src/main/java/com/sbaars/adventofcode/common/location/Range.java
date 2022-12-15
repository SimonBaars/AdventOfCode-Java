package com.sbaars.adventofcode.common.location;

import com.sbaars.adventofcode.common.Pair;

import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.Math.round;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.rangeClosed;

public class Range {
    public final Loc start;
    public final Loc end;

    public Range(Loc start, Loc end) {
        this.start = start;
        this.end = end;
    }

    public Range(long x1, long y1, long x2, long y2) {
        this(new Loc(x1, y1), new Loc(x2, y2));
    }

    public Range(Pair<Loc, Loc> pair) {
        this(pair.a(), pair.b());
    }

    public Stream<Loc> stream(){
        return rangeClosed(Math.min(start.intX(), end.intX()), Math.max(start.intX(), end.intX()))
                .boxed()
                .flatMap(x -> rangeClosed(Math.min(start.intY(), end.intY()), Math.max(start.intY(), end.intY())).mapToObj(y -> new Loc(x, y)));
    }

    public boolean inRange(Loc l) {
        return l.x >= start.x && l.y >=start.y && l.x <= end.x && l.y <= end.y;
    }

    public long distance() {
        return start.distance(end);
    }

    public Stream<Loc> flatten() {
        return Stream.of(start, end);
    }

    public Loc getStart() {
        return start;
    }

    public Loc getEnd() {
        return end;
    }

    public boolean inDiamond(Loc l) {
        return l.distance(start) <= distance();
    }

    public Optional<Loc> intersectsWith(Range r) {
        double x0 = start.x;
        double y0 = start.y;
        double x1 = end.x;
        double y1 = end.y;
        double x2 = r.start.x;
        double y2 = r.start.y;
        double x3 = r.end.x;
        double y3 = r.end.y;
        double s1_x, s1_y, s2_x, s2_y;
        s1_x = x1 - x0;     s1_y = y1 - y0;
        s2_x = x3 - x2;     s2_y = y3 - y2;

        double s, t;
        double denom = -s2_x * s1_y + s1_x * s2_y;
        s = (-s1_y * (x0 - x2) + s1_x * (y0 - y2)) / denom;
        t = ( s2_x * (y0 - y2) - s2_y * (x0 - x2)) / denom;
        return s >= 0 && s <= 1 && t >= 0 && t <= 1 ? of(new Loc(round(x0 + (t * s1_x)), round(y0 + (t * s1_y)))) : empty();
    }
}

package com.sbaars.adventofcode.common.location;

import com.sbaars.adventofcode.common.Pair;

import java.util.Optional;
import java.util.stream.Stream;

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
        long x1 = start.x;
        long y1 = start.y;
        long x2 = end.x;
        long y2 = end.y;
        long x3 = r.start.x;
        long y3 = r.start.y;
        long x4 = r.end.x;
        long y4 = r.end.y;
        long denominator = (x1-x2)*(y3-y4)-(y1-y2)*(y3-x4);
        if(denominator == 0) return empty();
        double px = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/denominator;
        double py = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/denominator;
        return of(new Loc((long)px, (long)py));
    }

    public Optional<Loc> intersectsWith2(Range r) {
        long x1 = start.x;
        long y1 = start.y;
        long x2 = end.x;
        long y2 = end.y;
        long x3 = r.start.x;
        long y3 = r.start.y;
        long x4 = r.end.x;
        long y4 = r.end.y;
        double denominator = (x1-x2)*(y3-y4)-(y1-y2)*(y3-x4);
        if(denominator == 0) return empty();
        double t = ((x1-x3)*(y3-y4)-(y1-y3)*(x3-x4))/denominator;
        return of(new Loc((long)t*(x2-x1), (long)t*(y2-y1)));
    }

    public Optional<Loc> intersectsWith3(Range r)
    {
        double p0_x = start.x;
        double p0_y = start.y;
        double p1_x = end.x;
        double p1_y = end.y;
        double p2_x = r.start.x;
        double p2_y = r.start.y;
        double p3_x = r.end.x;
        double p3_y = r.end.y;
        double s1_x, s1_y, s2_x, s2_y;
        s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
        s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

        double s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
        {
            return of(new Loc((long)(p0_x + (t * s1_x)), (long)(p0_y + (t * s1_y))));
        }

        return empty(); // No collision
    }


//    public Optional<Loc> intersectsWith2(Range r) {
//        long x1 = start.x;
//        long y1 = start.y;
//        long x2 = end.x;
//        long y2 = end.y;
//        long x3 = r.start.x;
//        long y3 = r.start.y;
//        long x4 = r.end.x;
//        long y4 = r.end.y;
//    }
}

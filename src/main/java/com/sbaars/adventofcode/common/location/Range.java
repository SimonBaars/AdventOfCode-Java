package com.sbaars.adventofcode.common.location;

import com.sbaars.adventofcode.common.Pair;

import java.util.stream.Stream;

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
}

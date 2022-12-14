package com.sbaars.adventofcode.common.location;

import java.awt.*;

public class MutableLoc {
    private Loc loc;

    public MutableLoc() {
        this(0, 0);
    }

    public MutableLoc(Loc p) {
        this(p.x, p.y);
    }

    public MutableLoc(long x, long y) {
        this.loc = new Loc(x, y);
    }

    public MutableLoc(Point p) {
        this(p.x, p.y);
    }
    public Loc move(int dx, int dy) {
        return set(loc.x + dx, loc.y + dy);
    }

    public Loc set(long x, long y) {
        this.loc = new Loc(x, y);
        return this.loc;
    }

    public Loc set(Loc l) {
        return set(l.x, l.y);
    }

    public Loc move(Loc l) {
        return set(loc.x + l.x, loc.y + l.y);
    }

    public Loc get() {
        return loc;
    }
}

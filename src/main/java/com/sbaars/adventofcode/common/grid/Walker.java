package com.sbaars.adventofcode.common.grid;

import com.sbaars.adventofcode.common.Direction;
import com.sbaars.adventofcode.common.location.Loc;

public record Walker(Loc loc, Direction direction) {
    public Walker walk() {
        return new Walker(loc.move(direction), direction);
    }

    public Walker walkBack() {
        return new Walker(loc.move(direction.opposite()), direction);
    }

    public Walker turn() {
        return new Walker(loc, direction.turn());
    }

    public Walker turn(int degrees) {
        return new Walker(loc, direction.turnDegrees(degrees));
    }

    public Walker turnBackwards() {
        return new Walker(loc, direction.opposite());
    }

    public Walker turnLeft() {
        return new Walker(loc, direction.turn(false));
    }

    public Walker turnRight() {
        return new Walker(loc, direction.turn());
    }

    public Walker turn(boolean left) {
        return new Walker(loc, direction.turn(left));
    }

    public Walker walkSteps(int steps) {
        return new Walker(loc, direction.turnSteps(steps));
    }
}

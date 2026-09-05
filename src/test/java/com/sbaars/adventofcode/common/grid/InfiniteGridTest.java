package com.sbaars.adventofcode.common.grid;

import com.sbaars.adventofcode.common.location.Loc;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InfiniteGridTest {

  @Test
  void getByCoordinatesDelegatesToLocWithoutRecursing() {
    InfiniteGrid grid = new InfiniteGrid();
    grid.set(new Loc(3, 4), 'X');

    assertEquals(Optional.of('X'), grid.get(3L, 4L));
    assertEquals(Optional.of('X'), grid.get(new Loc(3, 4)));
    assertTrue(grid.get(0L, 0L).isEmpty());
    assertEquals('X', grid.getOptimistic(3L, 4L));
  }
}

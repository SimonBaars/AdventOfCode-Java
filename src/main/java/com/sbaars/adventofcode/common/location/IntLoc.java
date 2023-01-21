/*
 * Copyright (c) 1995, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sbaars.adventofcode.common.location;

import java.awt.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntLoc {
  public final int x;
  public final int y;

  public IntLoc() {
    this(0, 0);
  }

  public IntLoc(IntLoc p) {
    this(p.x, p.y);
  }

  public IntLoc(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public IntLoc(Point p) {
    this.x = p.x;
    this.y = p.y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public IntLoc move(int dx, int dy) {
    return new IntLoc(x + dx, y + dy);
  }

  public IntLoc move(IntLoc l) {
    return new IntLoc(x + l.x, y + l.y);
  }

  public Point getPoint() {
    return new Point(Math.toIntExact(x), Math.toIntExact(y));
  }

  public static Stream<IntLoc> range(int i, int j) {
    return IntStream.range(0, i).boxed().flatMap(x -> IntStream.range(0, j).mapToObj(y -> new IntLoc(x, y)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    IntLoc intLoc = (IntLoc) o;

    if (x != intLoc.x) return false;
    return y == intLoc.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }

  @Override
  public String toString() {
    return getClass().getName() + "[x=" + x + ",y=" + y + "]";
  }
}

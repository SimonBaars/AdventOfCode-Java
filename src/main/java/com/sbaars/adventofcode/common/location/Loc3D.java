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

import java.util.List;

import static java.lang.Math.toIntExact;
import static java.util.stream.LongStream.rangeClosed;

public class Loc3D {
  public final long x;
  public final long y;
  public final long z;

  public Loc3D() {
    this(0, 0, 0);
  }

  public Loc3D(Loc3D p) {
    this(p.x, p.y, p.z);
  }

  public Loc3D(long x, long y, long z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Loc3D(long[] l) {
    this.x = l[0];
    this.y = l[1];
    this.z = l[2];
  }

  public List<Long> toList() {
    return List.of(x, y, z);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public int intX() {
    return toIntExact(x);
  }

  public int intY() {
    return toIntExact(y);
  }

  public int intZ() {
    return toIntExact(z);
  }

  public Loc3D move(long dx, long dy, long dz) {
    return new Loc3D(x + dx, y + dy, z + dz);
  }

  public Loc3D multiply(long dx, long dy, long dz) {
    return new Loc3D(x * dx, y * dy, z * dz);
  }

  public Loc3D divide(long dx, long dy, long dz) {
    return new Loc3D(x / dx, y / dy, z / dz);
  }

  public Loc3D move(Loc3D p) {
    return move(p.x, p.y, p.z);
  }

  public Loc3D multiply(Loc3D p) {
    return multiply(p.x, p.y, p.z);
  }

  public Loc3D multiply(long m) {
    return multiply(m, m, m);
  }

  public Loc3D divide(Loc3D p) {
    return divide(p.x, p.y, p.z);
  }

  public Loc3D divide(long m) {
    return divide(m, m, m);
  }

  public double distance(Loc3D p) {
    return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2) + Math.pow(z - p.getZ(), 2));
  }

  public Loc3D distanceTo(Loc3D b) {
    return new Loc3D(b.x - x, b.y - y, b.z - z);
  }

  public Loc3D flip(int flip) {
    return switch (flip) {
      case 0 -> this;
      case 1 -> new Loc3D(x, -y, -z);
      case 2 -> new Loc3D(x, -z, y);
      case 3 -> new Loc3D(-y, -z, x);
      case 4 -> new Loc3D(-x, -z, -y);
      case 5 -> new Loc3D(y, -z, -x);
      default -> throw new IllegalStateException("Invalid flip value: " + flip);
    };
  }

  public Loc3D rotate(int rot) {
    return switch (rot) {
      case 0 -> this;
      case 1 -> new Loc3D(-y, x, z);
      case 2 -> new Loc3D(-x, -y, z);
      case 3 -> new Loc3D(y, -x, z);
      default -> throw new IllegalStateException("Invalid rotation value: " + rot);
    };
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Loc3D loc3D = (Loc3D) o;

    if (x != loc3D.x) return false;
    if (y != loc3D.y) return false;
    return z == loc3D.z;
  }

  @Override
  public int hashCode() {
    int result = (int) (x ^ (x >>> 32));
    result = 31 * result + (int) (y ^ (y >>> 32));
    result = 31 * result + (int) (z ^ (z >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return x + ", " + y + ", " + z;
  }

  public boolean sameDistance(Loc3D m, Loc3D n) {
    return (x + m.x) == n.x && (y + m.y) == n.y && (z + m.z) == n.z;
  }

  public List<Loc3D> lineTo(Loc3D loc3D) {
    if (x < loc3D.x) {
      return rangeClosed(x, loc3D.x).mapToObj(i -> new Loc3D(i, y, z)).toList();
    } else if (y < loc3D.y) {
      return rangeClosed(y, loc3D.y).mapToObj(i -> new Loc3D(x, i, z)).toList();
    } else if (z < loc3D.z) {
      return rangeClosed(z, loc3D.z).mapToObj(i -> new Loc3D(x, y, i)).toList();
    } else if (equals(loc3D)) {
      return List.of(this);
    } else {
      throw new IllegalStateException("Invalid line: " + this + " to " + loc3D);
    }
  }
}

package com.sbaars.adventofcode.year21.days;

import static java.lang.Integer.parseInt;

import com.sbaars.adventofcode.common.IntLoc;
import com.sbaars.adventofcode.common.Loc3D;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import org.apache.commons.lang3.tuple.Pair;

public class Day19 extends Day2021 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  @Override
  public Object part1() {
    return findScannerPositions().getLeft().locs.size();
  }

  private Scanner[] scannerVariants(Scanner sc){
    Scanner[] variations = new Scanner[24];
    for (int up = 0; up < 6; up++) {
      for (int rot = 0; rot < 4; rot++) {
        variations[rot + up*4] = new Scanner(sc, up, rot);
      }
    }
    return variations;
  }

  protected Pair<Scanner, Loc3D[]> findScannerPositions() {
    Scanner[][] scanners = Arrays.stream(day().trim().split("\n\n")).map(Scanner::new).map(this::scannerVariants).toArray(Scanner[][]::new);
    var orientation = new Scanner[scanners.length];
    var position = new Loc3D[scanners.length];

    orientation[0] = scanners[0][0];
    position[0] = new Loc3D(0,0,0);

    Queue<Integer> frontier = new ArrayDeque<>();
    frontier.add(0);

    while (!frontier.isEmpty()) {
      var front = frontier.poll();
      for (int i = 0; i < scanners.length; i++) {
        if (position[i] == null) {
          var match = orientation[front].match(scanners[i]);
          if (match.isPresent()) {
            orientation[i] = match.get().getLeft();
            Loc3D one = position[front];
            Loc3D two = match.get().getRight();
            position[i] = new Loc3D(one.x+two.x, one.y+two.y, one.z+two.z);
            frontier.add(i);
          }
        }
      }
    }

    var result = new Scanner(orientation[0].id, new ArrayList<>(orientation[0].locs));
    for (int i = 1; i < scanners.length; i++) {
      result.add(orientation[i], position[i]);
    }
    return Pair.of(result, position);
  }

  @Override
  public Object part2() {
    var p = findScannerPositions().getRight();
    return IntLoc.range(p.length, p.length).mapToLong(l -> Math.abs(p[l.x].x-p[l.y].x) + Math.abs(p[l.x].y-p[l.y].y) + Math.abs(p[l.x].z-p[l.y].z)).max().getAsLong();
  }

  public record Scanner(long id, List<Loc3D> locs) {
    public Scanner(){
      this(0L, List.of());
    }

    public Scanner(String s){
      this(parseNumAt(12, s), Arrays.stream(s.substring(s.indexOf("\n")+1).split("\n")).map(e -> Arrays.stream(e.split(",")).mapToLong(Long::parseLong).toArray()).map(Loc3D::new).toList());
    }

    public Scanner(Scanner other, int up, int rot) {
      this(other.id, other.locs.stream().map(e -> e.flip(up).rotate(rot)).toList());
    }

    private Optional<Loc3D> findMatch(Scanner s) {
      for (int i = 0; i < locs.size(); i++) {
        for (int j = 0; j < s.locs.size(); j++) {
          var a = locs.get(i);
          var b = s.locs.get(j);
          var relx = b.x - a.x;
          var rely = b.y - a.y;
          var relz = b.z - a.z;
          int count = 0;
          for (int k = 0; k < locs.size(); k++) {
            if ((count + locs.size() - k) < 12) break;
            for (int l = 0; l < s.locs.size(); l++) {
              var m = locs.get(k);
              var n = s.locs.get(l);
              if ((relx + m.x) == n.x && (rely + m.y) == n.y && (relz + m.z) == n.z) {
                count++;
                if (count >= 12) return Optional.of(new Loc3D(relx, rely, relz));
                break;
              }
            }
          }
        }
      }
      return Optional.empty();
    }

    public Optional<Pair<Scanner, Loc3D>> match(Scanner[] other) {
      for (int i = 0; i < other.length; i++) {
        var sc = other[i];
        var mat = findMatch(sc);
        if (mat.isPresent()) return Optional.of(Pair.of(sc, mat.get()));
      }
      return Optional.empty();
    }

    public void add(Scanner s, Loc3D p) {
      for (int l = 0; l < s.locs.size(); l++) {
        var n = s.locs.get(l);
        n = new Loc3D(n.x - p.x, n.y - p.y, n.z - p.z);
        if (!locs.contains(n)) locs.add(n);
      }
    }
  }

  static int parseNumAt(int i, String s){
    if(!isNum(s.charAt(i))) return -1;
    int j = i;
    for(; isNum(s.charAt(j)); j++);
    for(; isNum(s.charAt(i)); i--);
    return parseInt(s.substring(i+1, j));
  }

  static boolean isNum(char c){
    return c >= '0' && c <= '9';
  }
}

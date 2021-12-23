package com.sbaars.adventofcode.year21.days;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.MoreObjects;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23 extends Day2021 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().verify();
    new Day23().printParts();
//    new Day23().submitPart1();
//    new Day23().submitPart2();
  }
  
  public void verify(){
    List<State> l = new ArrayList<>(List.of(new State(new int[][]{{0,0},{1,1},{2,2},{3,3}}, new int[]{-1,-1,-1,-1,-1,-1,-1}, 0L)));
    checkState(l.get(0).win());
    simulateMoves(l);
    checkState(l.isEmpty());
    l = new ArrayList<>(List.of(new State(new int[][]{{0,0},{1,1},{2,2},{-1,3}}, new int[]{-1,-1,-1,-1,-1,-1,3}, 0L)));
    simulateMoves(l);
    checkState(l.size() == 1);
    checkState(l.get(0).win());
    l = new ArrayList<>(List.of(new State(new int[][]{{0,0},{2,1},{1,2},{3,3}}, new int[]{-1,-1,-1,-1,-1,-1,-1}, 0L)));
    Optional<Long> out = Optional.empty();
    while(!l.isEmpty()) out = simulateMoves(l);
    checkState(out.isPresent());
  }

  @Override
  public Object part1() {
    var rooms = new int[][]{{2, 1}, {3, 0}, {0, 3}, {1, 2}};
    int[] waiting = new int[7];
    Arrays.fill(waiting, -1);
    List<State> states = new ArrayList<>(List.of(new State(rooms, waiting, 0)));
    long cur = Long.MAX_VALUE;
    while(!states.isEmpty()){
      Optional<Long> sim = simulateMoves(states);
      if(sim.isPresent() && sim.get()<cur) {
        cur = sim.get();
        System.out.println(cur);
      }
    }
    return cur;
  }

  private Optional<Long> simulateMoves(List<State> states) {
    State s = states.remove(0);
    if(s.win()){
      return Optional.of(s.energySpent);
    }
    int[] occupied = s.occupiedRooms();
    for(int o : occupied){
      int[] freeWait = s.freeWaiting(o);
      for(int w : freeWait){
        states.add(s.moveIntoWaiting(o, w));
      }
    }
    occupied = s.occupiedWaiting();
    for(int o : occupied){
      int freeRoom = s.freeRoom(o);
      if(freeRoom!=-1){
        states.add(s.moveFromWaiting(o, freeRoom));
      }
    }
    return Optional.empty();
  }

  public record State(int[][] rooms, int[] waiting, long energySpent){
    public State copy(long newEnergy){
      int[][] room = Arrays.stream(rooms).map(int[]::clone).toArray(int[][]::new);;
      int[] wait = Arrays.copyOf(waiting, waiting.length);
      return new State(room, wait, energySpent + newEnergy);
    }

    public State moveIntoWaiting(int r, int w){
      int toMove = rooms[r][0] == -1 ? 1 : 0;
      State s = copy(energy(rooms[r][toMove]) * (energyMultiplier(r, w) + toMove));
      s.waiting[w] = s.rooms[r][toMove];
      s.rooms[r][toMove] = -1;
      return s;
    }

    public long energy(int x){
      return switch(x) {
        case 0 -> 1L;
        case 1 -> 10L;
        case 2 -> 100L;
        case 3 -> 1000L;
        default -> throw new IllegalStateException("Invalid "+x);
      };
    }

    public long energyMultiplier(int r, int w) {
      if(r == 0 && w == 0) {
        return 3;
      } else if(r == 0 && w == 1) {
        return 2;
      } else if(r == 0 && w == 2) {
        return 2;
      } else if(r == 0 && w == 3) {
        return 4;
      } else if(r == 0 && w == 4) {
        return 6;
      } else if(r == 0 && w == 5) {
        return 8;
      } else if(r == 0 && w == 6) {
        return 9;
      } else if(r == 1 && w == 0) {
        return 5;
      } else if(r == 1 && w == 1) {
        return 4;
      } else if(r == 1 && w == 2) {
        return 2;
      } else if(r == 1 && w == 3) {
        return 2;
      } else if(r == 1 && w == 4) {
        return 4;
      } else if(r == 1 && w == 5) {
        return 6;
      } else if(r == 1 && w == 6) {
        return 7;
      } else if(r == 2 && w == 0) {
        return 7;
      } else if(r == 2 && w == 1) {
        return 6;
      } else if(r == 2 && w == 2) {
        return 4;
      } else if(r == 2 && w == 3) {
        return 2;
      } else if(r == 2 && w == 4) {
        return 2;
      } else if(r == 2 && w == 5) {
        return 4;
      } else if(r == 2 && w == 6) {
        return 5;
      } else if(r == 3 && w == 0) {
        return 9;
      } else if(r == 3 && w == 1) {
        return 8;
      } else if(r == 3 && w == 2) {
        return 6;
      } else if(r == 3 && w == 3) {
        return 4;
      } else if(r == 3 && w == 4) {
        return 2;
      } else if(r == 3 && w == 5) {
        return 2;
      } else if(r == 3 && w == 6) {
        return 3;
      }
      throw new IllegalStateException("Invalid move "+r+", "+w);
    }

    public State moveFromWaiting(int w, int r){
      int toMove = rooms[r][1] == -1 ? 1 : 0;
      State s = copy(energy(waiting[w]) * (energyMultiplier(r, w) + toMove));
      s.rooms[r][toMove] = s.waiting[w];
      s.waiting[w] = -1;
      return s;
    }

    public int[] occupiedRooms(){
      return IntStream.range(0, rooms.length).filter(e -> IntStream.of(rooms[e]).anyMatch(f -> f!=-1) && !IntStream.of(rooms[e]).allMatch(f -> f==e) && !(rooms[e][0] == -1 && rooms[e][1] == e)).toArray();
    }

    public int[] occupiedWaiting(){
      return IntStream.range(0, waiting.length).filter(e -> waiting[e] != -1).toArray();
    }

    public int freeRoom(int forWaiting){
      int room = waiting[forWaiting];
      if(rooms[room][1] != -1 && rooms[room][1] != room) return -1;
      int r = room + 1;
      for(int i = forWaiting+1; i<=r; i++){
        if(waiting[i] != -1){
          return -1;
        }
      }
      for(int i = forWaiting-1; i>r; i--){
        if(waiting[i] != -1){
          return -1;
        }
      }
      return room;
    }

    // Indexes of free waiting spots
    public int[] freeWaiting(int forRoom){
      if(rooms[forRoom][0] == forRoom) return new int[0];
      var seeminglyFree = IntStream.range(0, waiting.length).filter(e -> waiting[e] == -1).boxed().collect(Collectors.toCollection(HashSet::new));
      if(seeminglyFree.contains(0) && !seeminglyFree.contains(1)){
        seeminglyFree.remove(0);
      }
      if(seeminglyFree.contains(6) && !seeminglyFree.contains(5)){
        seeminglyFree.remove(6);
      }
      int r = forRoom+1;
      for(int i = r; i>=0; i--){
        if(!seeminglyFree.contains(r)){
          int i2 = i;
          seeminglyFree.removeIf(x -> x<i2);
          break;
        }
      }
      for(int i = r+1; i<waiting.length; i++){
        if(!seeminglyFree.contains(r)){
          int i2 = i;
          seeminglyFree.removeIf(x -> x>i2);
          break;
        }
      }
      return seeminglyFree.stream().mapToInt(e -> e).toArray();
    }

    public boolean win(){
      return IntStream.range(0, rooms.length).allMatch(e -> IntStream.of(rooms[e]).allMatch(f -> f==e));
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("rooms", rooms)
          .add("waiting", waiting)
          .add("energySpent", energySpent)
          .toString();
    }
  }

  @Override
  public Object part2() {
    return "";
  }
}

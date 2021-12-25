package com.sbaars.adventofcode.year21.days;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.MoreObjects;
import com.sbaars.adventofcode.year21.Day2021;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;

public class Day23 extends Day2021 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().verify();
    new Day23().printParts(1);
//    new Day23().part2();
  }
  
  public void verify(){
    Queue<State> l = getQueue(new State(new int[][]{{0,0},{1,1},{2,2},{3,3}}, new int[]{-1,-1,-1,-1,-1,-1,-1}));
    checkState(l.peek().win());
    simulateMoves(l, new HashSet<>());
    checkState(l.isEmpty());
    l = getQueue(new State(new int[][]{{0,0},{1,1},{2,2},{-1,3}}, new int[]{-1,-1,-1,-1,-1,-1,3}));
    simulateMoves(l, new HashSet<>());
    checkState(l.size() == 1);
    checkState(l.poll().win());
    l = getQueue(new State(new int[][]{{0,0},{2,1},{1,2},{3,3}}, new int[]{-1,-1,-1,-1,-1,-1,-1}));
    checkState(getMinimum(l, new HashSet<>()) == (100L*4)+(10L*6));
    l = getQueue(new State(new int[][]{{0,0},{2,2},{1,1},{3,3}}, new int[]{-1,-1,-1,-1,-1,-1,-1}));
    checkState(getMinimum(l, new HashSet<>()) == (100L*10)+(10L*14));
    l = getQueue(new State(new int[][]{{0,0,0,0},{2,2,2,2},{1,1,1,1},{3,3,3,3}}, new int[]{-1,-1,-1,-1,-1,-1,-1}));
    checkState(getMinimum(l, new HashSet<>()) == 3240L);
  }

  @Override
  public Object part1() {
    if(example == 1) {
      return constructField(new int[][]{{1, 0}, {2, 3}, {1, 2}, {3, 0}});
    }
    return constructField(new int[][]{{2, 1}, {3, 0}, {0, 3}, {1, 2}});
  }

  private long constructField(int[][] rooms) {
    int[] waiting = new int[7];
    Arrays.fill(waiting, -1);
    Set<State> stateSet = new HashSet<>();
    Queue<State> states = getQueue(new State(rooms, waiting, 0, 0));
    return getMinimum(states, stateSet);
  }

  private PriorityQueue<State> getQueue(State s) {
    var queue = new PriorityQueue<>(1000, Comparator.comparing(State::energySpent));
    queue.add(s);
    return queue;
  }

  private long getMinimum(Queue<State> states, Set<State> stateSet) {
    while(true){
      Optional<Long> sim = simulateMoves(states, stateSet);
      if(sim.isPresent()) {
        return sim.get();
      }
    }
  }

  private Optional<Long> simulateMoves(Queue<State> states, Set<State> stateSet) {
    State s = states.poll();
    if(stateSet.contains(s)) return Optional.empty();
    stateSet.add(s);
    if(s.win()){
      return Optional.of(s.energySpent);
    }
    int[] occupied = s.occupiedRooms();
    for(int r : occupied){
      int[] freeWait = s.freeWaiting(r);
      for(int w : freeWait){
        states.add(s.moveIntoWaiting(r, w));
      }
    }
    occupied = s.occupiedWaiting();
    for(int w : occupied){
      int r = s.freeRoom(w);
      if(r!=-1){
        states.add(s.moveFromWaiting(w, r));
      }
    }
    return Optional.empty();
  }

  public record State(int[][] rooms, int[] waiting, long energySpent, long moves){
    public State(int[][] rooms, int[] waiting){
      this(rooms, waiting, 0L, 0L);
    }

    public State copy(long newEnergy){
      int[][] room = Arrays.stream(rooms).map(int[]::clone).toArray(int[][]::new);;
      int[] wait = Arrays.copyOf(waiting, waiting.length);
      return new State(room, wait, energySpent + newEnergy, moves + 1);
    }

    public State moveIntoWaiting(int r, int w){
      int toMove = ArrayUtils.lastIndexOf(rooms[r], -1) + 1;
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
      int toMove = ArrayUtils.lastIndexOf(rooms[r], -1);
      if(toMove == -1){
        toMove = 0;
      }
      State s = copy(energy(waiting[w]) * (energyMultiplier(r, w) + toMove));
      s.rooms[r][toMove] = s.waiting[w];
      s.waiting[w] = -1;
      return s;
    }

    public int[] occupiedRooms(){
        return IntStream.range(0, rooms.length).filter(e -> !IntStream.of(rooms[e]).allMatch(r->r ==e || r == -1)).toArray();
    }

    public int[] occupiedWaiting(){
      return IntStream.range(0, waiting.length).filter(e -> waiting[e] != -1).toArray();
    }

    public int freeRoom(int forWaiting){
      int room = waiting[forWaiting];
      if(rooms[room][rooms[room].length-1] != -1 && rooms[room][rooms[room].length-1] != room) return -1;
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

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      State state = (State) o;
      return Arrays.deepEquals(rooms, state.rooms) && Arrays.equals(waiting, state.waiting);
    }

    @Override
    public int hashCode() {
      return Arrays.deepHashCode(rooms) + Arrays.hashCode(waiting);
    }
  }

  @Override
  public Object part2() {
    if(example == 1) {
      return constructField(new int[][]{{1, 3, 3, 0}, {2, 2, 1, 3}, {1, 1, 0, 2}, {3, 0, 2, 0}});
    }
    return constructField(new int[][]{{2, 3, 3, 1}, {3, 2, 1, 0}, {0, 1, 0, 3}, {1, 0, 2, 2}});
  }
}

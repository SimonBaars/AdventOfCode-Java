package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 extends Day2019 {

  Move[] moves;

  public Day22() {
    super(22);
    this.moves = Arrays.stream(day().split(System.lineSeparator())).map(Move::parse).toArray(Move[]::new);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  private static BigInteger num(long n) {
    return new BigInteger(Long.toString(n));
  }

  @Override
  public Object part1() {
    List<Integer> cards = IntStream.range(0, 10007).boxed().collect(Collectors.toList());
    for (Move move : moves) cards = move.execute(cards);
    return cards.indexOf(2019);
  }

  @Override
  public Object part2() {
    return seekPosition(num(119315717514047L), num(101741582076661L), 2020);
  }

  private BigInteger seekPosition(BigInteger deckSize, BigInteger timesShuffled, int position) {
    BigInteger[] calc = new BigInteger[]{num(1), num(0)};
    for (Move move : reverseArray(moves)) {
      move.execute(calc, deckSize);
      for (int i = 0; i < calc.length; i++) calc[i] = calc[i].mod(deckSize);
    }
    BigInteger pow = calc[0].modPow(timesShuffled, deckSize);
    return pow.multiply(num(position)).add(calc[1].multiply(pow.add(deckSize).subtract(num(1))).multiply(calc[0].subtract(num(1)).modPow(deckSize.subtract(num(2)), deckSize))).mod(deckSize);
  }

  private <T> T[] reverseArray(T[] arr) {
    for (int i = 0; i < arr.length / 2; i++) {
      T temp = arr[i];
      arr[i] = arr[arr.length - i - 1];
      arr[arr.length - i - 1] = temp;
    }
    return arr;
  }

  enum Action {
    DEAL_WITH_INCREMENT("deal with increment "),
    DEAL_NEW_STACK("deal into new stack"),
    CUT("cut ");

    String name;

    Action(String name) {
      this.name = name;
    }

    public static Action actionByText(String text) {
      return Arrays.stream(values()).filter(a -> text.startsWith(a.name)).findAny().get();
    }
  }

  record Move(Action action, int amount) {

    public static Move parse(String s) {
      Action action = Action.actionByText(s);
      s = s.replace(action.name, "");
      return new Move(action, !s.isEmpty() ? Integer.parseInt(s) : 0);
    }

    public List<Integer> execute(List<Integer> cards) {
      switch (action) {
        case DEAL_NEW_STACK:
          Collections.reverse(cards);
          break;
        case CUT: {
          int n = amount > 0 ? amount : cards.size() + amount;
          List<Integer> sub = new ArrayList<>(cards.subList(n, cards.size()));
          sub.addAll(cards.subList(0, n));
          return sub;
        }
        case DEAL_WITH_INCREMENT: {
          Integer[] deck = new Integer[cards.size()];
          for (int i = 0, card = 0; i < cards.size(); i++) {
            deck[card] = cards.get(i);
            card = (card + amount) % deck.length;
          }
          return Arrays.asList(deck);
        }
      }
      return cards;
    }

    public void execute(BigInteger[] input, BigInteger deckSize) {
      switch (action) {
        case DEAL_NEW_STACK: {
          input[0] = input[0].multiply(num(-1));
          input[1] = input[1].add(num(1)).multiply(num(-1));
        }
        break;
        case CUT:
          input[1] = input[1].add(num(amount));
          break;
        case DEAL_WITH_INCREMENT: {
          BigInteger p = num(amount).modPow(deckSize.subtract(num(2)), deckSize);
          for (int i = 0; i < input.length; i++) input[i] = input[i].multiply(p);
        }
        break;
      }
    }
  }
}

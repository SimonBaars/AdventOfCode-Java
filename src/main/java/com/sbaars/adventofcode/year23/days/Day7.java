package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.map.CountMap;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.Comparator;

import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static com.sbaars.adventofcode.util.AOCUtils.zipWithIndex;
import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.stream.IntStream.range;

public class Day7 extends Day2023 {

    public Day7() {
        super(7);
    }

    public static void main(String[] args) {
        new Day7().printParts();
    }

    public record Hand(String cards, int bet) {
    }

    @Override
    public Object part1() {
        return solve(this::compareHands);
    }

    @Override
    public Object part2() {
        return solve(this::compareHands2);
    }

    private long solve(Comparator<Hand> comparator) {
        return zipWithIndex(
                dayStream().map(s -> readString(s, "%s %i", Hand.class)).sorted(comparator)
                ).mapToLong(hand -> (long) hand.e().bet * (hand.i() + 1)).sum();
    }

    public int compareHands(Hand h1, Hand h2) {
        int setRank = Integer.compare(getRank(h1.cards), getRank(h2.cards));
        if (setRank != 0) return setRank;
        for (int i = 0; i < h1.cards.length(); i++) {
            int cardValue = Integer.compare(cardValue(h1.cards.charAt(i)), cardValue(h2.cards.charAt(i)));
            if (cardValue != 0) return cardValue;
        }
        return 0;
    }

    public int getRank(String cards) {
        CountMap<Character> charCounts = getCharacterCounts(cards);
        if (charCounts.containsValue(5)) {
            return 7;
        } else if (charCounts.containsValue(4)) {
            return 6;
        } else if (charCounts.containsValue(3) && charCounts.containsValue(2)) {
            return 5;
        } else if (charCounts.containsValue(3)) {
            return 4;
        } else if (charCounts.values().size() == 3) {
            return 2;
        } else if (charCounts.values().size() == 4) {
            return 1;
        }
        return 0;
    }

    public int getRankJ(String cards) {
        CountMap<Character> charCounts = getCharacterCounts(cards);
        if (charCounts.containsValue(4)) {
            return 7;
        } else if (charCounts.containsValue(3)) {
            return 6;
        } else if (charCounts.values().stream().allMatch(i -> i == 2)) {
            return 5;
        } else if (charCounts.containsValue(2)) {
            return 4;
        }
        return 1;
    }

    public int getRankJJ(String cards) {
        CountMap<Character> charCounts = getCharacterCounts(cards);
        if (charCounts.containsValue(3)) {
            return 7;
        } else if (charCounts.containsValue(2)) {
            return 6;
        }
        return 4;
    }

    public int setRankJJJ(String cards) {
        CountMap<Character> charCounts = getCharacterCounts(cards);
        if (charCounts.containsValue(2)) {
            return 7;
        }
        return 6;
    }

    private static CountMap<Character> getCharacterCounts(String cards) {
        CountMap<Character> charCounts = new CountMap<>();
        for (char c : cards.toCharArray()) {
            charCounts.increment(c);
        }
        return charCounts;
    }

    public int cardValue(char c) {
        return switch (c) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            default -> c - '0';
        };
    }

    public int compareHands2(Hand h1, Hand h2) {
        int setRank = Integer.compare(resolveWildcards(h1.cards), resolveWildcards(h2.cards));
        if (setRank != 0) return setRank;
        for (int i = 0; i < h1.cards.length(); i++) {
            int cardValue = Integer.compare(cardValue2(h1.cards.charAt(i)), cardValue2(h2.cards.charAt(i)));
            if (cardValue != 0) return cardValue;
        }
        return 0;
    }

    public int resolveWildcards(String cards) {
        CountMap<Character> charCounts = getCharacterCounts(cards);
        if (!charCounts.containsKey('J')) return getRank(cards);

        cards = cards.replace("J", "");
        return switch(charCounts.get('J')) {
            case 1 -> getRankJ(cards);
            case 2 -> getRankJJ(cards);
            case 3 -> setRankJJJ(cards);
            case 4,5 -> 7;
            default -> throw new IllegalStateException();
        };

    }

    public int cardValue2(char c) {
        return switch (c) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 1;
            case 'T' -> 10;
            default -> c - '0';
        };
    }
}

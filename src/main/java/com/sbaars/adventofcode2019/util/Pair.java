package com.sbaars.adventofcode2019.util;

public class Pair<K, V> {
	public final K fst;
	public final V snd;
	
	public Pair(K key, V value) {
		super();
		this.fst = key;
		this.snd = value;
	}
}

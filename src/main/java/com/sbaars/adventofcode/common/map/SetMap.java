package com.sbaars.adventofcode.common.map;

import java.util.*;

public class SetMap<K, V> extends HashMap<K, Set<V>> {
	public SetMap() {
	}

	public SetMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public SetMap(int initialCapacity) {
		super(initialCapacity);
	}

	public SetMap(Map<? extends K, ? extends Set<V>> m) {
		super(m);
	}
	
	public Set<V> addTo(K key, V value) {
		Set<V> l;
		if(super.containsKey(key)) {
			l = super.get(key); 
		} else l = new HashSet<>();
		l.add(value);
        return super.put(key, l);
    }
	
	public Set<V> addTo(K key, V[] value) {
		Set<V> l;
		if(super.containsKey(key)) {
			l = super.get(key); 
		} else l = new HashSet<>();
		Collections.addAll(l, value);
        return super.put(key, l);
    }

	public Set<V> addTo(K key, Collection<V> value) {
		Set<V> l;
		if(super.containsKey(key)) {
			l = super.get(key);
		} else l = new HashSet<>();
		l.addAll(value);
		return super.put(key, l);
	}

	public SetMap<K, V> mergeWith(SetMap<K, V> map) {
		map.forEach(this::addTo);
		return this;
	}

	public SetMap<K, V> removeFrom(K key, V value) {
		get(key).remove(value);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public Set<V> get(Object key){
		if(!super.containsKey(key))
			super.put((K) key, new HashSet<>());
		return super.get(key);
	}
	
	public Entry<K, Set<V>> getEntryForValue(V i) {
		System.out.println(Arrays.toString(values().toArray()));
		Optional<Entry<K, Set<V>>> findAny = entrySet().stream().filter(e -> e.getValue().contains(i)).findAny();
		if(!findAny.isPresent())
			throw new IllegalAccessError("Value "+i+" does not exist in this map!");
		return findAny.get();
	}

	public boolean hasValue(V v) {
		return values().stream().anyMatch(e -> e.contains(v));
	}
}
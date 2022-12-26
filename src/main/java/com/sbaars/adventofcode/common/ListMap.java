package com.sbaars.adventofcode.common;

import java.util.*;

public class ListMap<K, V> extends HashMap<K, List<V>> {
	public ListMap() {
	}

	public ListMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ListMap(int initialCapacity) {
		super(initialCapacity);
	}

	public ListMap(Map<? extends K, ? extends List<V>> m) {
		super(m);
	}
	
	public List<V> addTo(K key, V value) {
		List<V> l;
		if(super.containsKey(key)) {
			l = super.get(key); 
		} else l = new ArrayList<>();
		l.add(value);
        return super.put(key, l);
    }
	
	public List<V> addTo(K key, V[] value) {
		List<V> l;
		if(super.containsKey(key)) {
			l = super.get(key); 
		} else l = new ArrayList<>();
		Collections.addAll(l, value);
        return super.put(key, l);
    }

	public List<V> addTo(K key, Collection<V> value) {
		List<V> l;
		if(super.containsKey(key)) {
			l = super.get(key);
		} else l = new ArrayList<>();
		l.addAll(value);
		return super.put(key, l);
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public List<V> get(Object key){
		if(!super.containsKey(key))
			super.put((K) key, new ArrayList<>());
		return super.get(key);
	}
	
	public Entry<K, List<V>> getEntryForValue(V i) {
		System.out.println(Arrays.toString(values().toArray()));
		Optional<Entry<K, List<V>>> findAny = entrySet().stream().filter(e -> e.getValue().contains(i)).findAny();
		if(!findAny.isPresent())
			throw new IllegalAccessError("Value "+i+" does not exist in this map!");
		return findAny.get();
	}
}
// /*
//  * @(#)CachedArrayList.java 10/5/2005
//  *
//  * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
//  */
// package com.element.util;
//
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.IdentityHashMap;
// import java.util.Map;
//
// /**
//  * 这是一个快速访问的 ArrayList，它牺牲了内存来换取速度。它会将 indexOf 方法的速度从 O(n) 降低到 O(1)。然而，它至少会使 ArrayList 使用的内存增加一倍。所以适当地使用它。
//  *
//  * <strong>就像 ArrayList 一样，这个实现是不同步的。</strong>如果你想要一个线程安全的实现，你可以使用CachedVector 。
//  */
// public class CachedArrayList<E> extends ArrayList<E> {
// 	private Map<Object, IntegerWrapper> _indexCache;
// 	private boolean _lazyCaching = false;
//
// 	public CachedArrayList() {
// 	}
//
// 	public CachedArrayList(Collection<? extends E> c) {
// 		super(c);
// 		if (!isLazyCaching()) {
// 			cacheAll();
// 		}
// 	}
//
// 	public CachedArrayList(int initialCapacity) {
// 		super(initialCapacity);
// 	}
//
// 	@Override
// 	public int indexOf(Object elem) {
// 		initializeCache();
// 		IntegerWrapper o = _indexCache.get(elem);
// 		if (o != null) {
// 			return o.integer;
// 		} else if (isLazyCaching()) {
// 			int i = super.indexOf(elem);
// 			if (i == -1) {
// 				uncacheIt(elem);
// 			} else {
// 				cacheIt(elem, i);
// 			}
// 			return i;
// 		} else {
// 			return -1;
// 		}
// 	}
//
// 	/**
// 	 * Adjusts the cache so that all values that are greater than index will increase by the value specified by the
// 	 * increase parameter.
// 	 *
// 	 * @param index    the index. All values above this index will be changed.
// 	 * @param increase a positive number to increase or a negative number to decrease.
// 	 */
// 	protected synchronized void adjustCache(int index, int increase) {
// 		if (_indexCache != null) {
// 			if (size() - index <= size() >> 2) {
// 				Collection<IntegerWrapper> values = _indexCache.values();
// 				for (IntegerWrapper value : values) {
// 					if (value.integer >= index) {
// 						value.integer += increase;
// 					}
// 				}
// 			} else {
// 				uncacheAll();
// 				if (!isLazyCaching()) {
// 					cacheAll();
// 				}
// 			}
// 		}
// 	}
//
// 	protected Map<Object, IntegerWrapper> createCache() {
// 		return new IdentityHashMap<>();
// 	}
//
// 	/**
// 	 * Caches the index of the element.
// 	 *
// 	 * @param o     the element
// 	 * @param index the index.
// 	 */
// 	public synchronized void cacheIt(Object o, int index) {
// 		if (_indexCache != null && (_indexCache.get(o) == null || index < _indexCache.get(o).integer)) {
// 			_indexCache.put(o, new IntegerWrapper(index));
// 		}
// 	}
//
// 	/**
// 	 * Uncaches the index of the element.
// 	 *
// 	 * @param o the element
// 	 */
// 	public synchronized void uncacheIt(Object o) {
// 		if (_indexCache != null) {
// 			_indexCache.remove(o);
// 		}
// 	}
//
// 	@Override
// 	public boolean add(E element) {
// 		boolean added = super.add(element);
// 		if (!isLazyCaching() && added) {
// 			initializeCache();
// 			cacheIt(element, size() - 1);
// 		}
// 		return added;
// 	}
//
// 	@Override
// 	public void add(int index, E element) {
// 		super.add(index, element);
// 		if (!isLazyCaching()) {
// 			initializeCache();
// 			adjustCache(index, 1);
// 			cacheIt(element, index);
// 		} else if (_indexCache != null) {
// 			adjustCache(index, 1);
// 			cacheIt(element, index);
// 		}
// 	}
//
// 	private void initializeCache() {
// 		if (_indexCache == null) {
// 			_indexCache = createCache();
// 		}
// 	}
//
// 	@Override
// 	public E remove(int index) {
// 		E element = super.remove(index);
// 		if (element != null) {
// 			uncacheIt(element);
// 			adjustCache(index, -1);
// 		}
// 		return element;
// 	}
//
// 	@Override
// 	public boolean remove(Object o) {
// 		int oldIndex = indexOf(o);
// 		boolean removed = super.remove(o);
// 		if (removed) {
// 			uncacheIt(o);
// 			adjustCache(oldIndex, -1);
// 		}
// 		return removed;
// 	}
//
// 	@Override
// 	public boolean removeAll(Collection<?> c) {
// 		uncacheAll();
// 		return super.removeAll(c);
// 	}
//
//
// 	@Override
// 	public void clear() {
// 		uncacheAll();
// 		super.clear();
// 	}
//
//
// 	@Override
// 	public boolean addAll(Collection<? extends E> c) {
// 		boolean added = super.addAll(c);
// 		if (added) {
// 			cacheAll();
// 		}
// 		return added;
// 	}
//
// 	@Override
// 	public boolean addAll(int index, Collection<? extends E> c) {
// 		boolean added = super.addAll(index, c);
// 		initializeCache();
// 		adjustCache(index, c.size());
// 		for (E e : c) {
// 			cacheIt(e, index++);
// 		}
// 		return added;
// 	}
//
// 	@Override
// 	public E set(int index, E element) {
// 		if (!isLazyCaching()) {
// 			initializeCache();
// 			E e = super.set(index, element);
// 			uncacheIt(e);
// 			cacheIt(element, index);
// 			return e;
// 		} else {
// 			return super.set(index, element);
// 		}
// 	}
//
// 	/**
// 	 * Invalidated the whole cache.
// 	 */
// 	public void invalidateCache() {
// 		uncacheAll();
// 	}
//
// 	/**
// 	 * Uncache the whole cache. It is the same as {@link #invalidateCache()}.
// 	 */
// 	public synchronized void uncacheAll() {
// 		if (_indexCache != null) {
// 			_indexCache.clear();
// 			_indexCache = null;
// 		}
// 	}
//
// 	/**
// 	 * Cache all the element index.
// 	 */
// 	public synchronized void cacheAll() {
// 		_indexCache = createCache();
// 		int i = 0;
// 		for (Object elem : this) {
// 			if (_indexCache.get(elem) == null) {
// 				_indexCache.put(elem, new IntegerWrapper(i));
// 			}
// 			i++;
// 		}
// 	}
//
// 	public boolean isLazyCaching() {
// 		return _lazyCaching;
// 	}
//
// 	public void setLazyCaching(boolean lazyCaching) {
// 		_lazyCaching = lazyCaching;
// 	}
//
// 	@Override
// 	protected void removeRange(int fromIndex, int toIndex) {
// 		if (fromIndex == toIndex) {
// 			remove(fromIndex);
// 		} else {
// 			super.removeRange(fromIndex, toIndex);
// 			uncacheAll();
// 			if (!isLazyCaching()) {
// 				cacheAll();
// 			}
// 		}
// 	}
//
// 	public static class IntegerWrapper {
// 		int integer;
//
// 		private IntegerWrapper(int integer) {
// 			this.integer = integer;
// 		}
// 	}
// }

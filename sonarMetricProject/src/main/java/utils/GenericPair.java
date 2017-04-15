/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.utils;

import  java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/**
 * Class to represent pair of values
 * @author Tomas Lestyan
 * @param <L> 
 * @param <R> 
 */
public class GenericPair<L, R> {
	Entry<L,R> pair;


	/**
	 * Constructor
	 * @param left
	 * @param right
	 */
	public GenericPair(L left, R right) {
		this.pair = new SimpleEntry<>(left, right);
	}

	/**
	 * @return the left value
	 */
	public L getLeft() {
		return pair.getKey();
	}

	/**
	 * @return the right value
	 */
	public R getRight() {
		return pair.getValue();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pair == null) ? 0 : pair.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GenericPair)) {
			return false;
		}
		GenericPair<L, R> other = (GenericPair<L, R>) obj;
		if (!other.getLeft().equals(this.getLeft())) {
			return false;
		}
		if (!other.getRight().equals(this.getRight())) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pair (" + getLeft() + ",  " + getRight()+ ")";
	}

}

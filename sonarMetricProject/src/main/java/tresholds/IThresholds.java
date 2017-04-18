/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

/**
 * Interface of thresholds handling classes
 * @author Tomas Lestyan
 */
public interface IThresholds {

	/**
	 * Get the default treshold value
	 * @param key
	 * @return value of the treshold
	 */
	public int getTresholdValueOf(String key);

	/**
	 * Get the default threshold value by percentile semantics
	 * @see PercentileSemantics
	 * @param key
	 * @param percentile
	 * @return value of the treshold
	 */
	public int getTresholdValueOf(String key, double percentile);
}

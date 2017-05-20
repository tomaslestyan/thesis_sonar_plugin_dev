/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

import java.util.Collection;

import main.java.disharmonies.parser.Semantic;

/**
 * Interface of thresholds handling classes
 * @author Tomas Lestyan
 */
public interface IThresholds {

	/**
	 * Initialize the thresholds with given semantics. 
	 * Must be called for proper functionality of thresholds.
	 * @param semantics
	 */
	public void init(Collection<Semantic> semantics);

	/**
	 * Get the default threshold value by percentile semantics
	 * @see PercentileSemantics
	 * @param metricID
	 * @param percentile
	 * @return value of the treshold
	 */
	public int getTresholdValueOf(String metricID, double percentile);

	/**
	 * Get the default threshold value by percentile semantics
	 * @see PercentileSemantics
	 * @param metricID
	 * @param semantics
	 * @return value of the treshold
	 */
	public int getTresholdValueOf(String metricID, String semantics);
}

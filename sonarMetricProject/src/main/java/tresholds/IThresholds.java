/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

import org.sonar.plugins.java.api.tree.Tree;

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

	/**
	 * Save the value of the treshold to buffer
	 * @param tree
	 * @param key
	 * @param value
	 */
	public void saveTresholdValue(Tree tree, String key, int value);

	/**
	 * Save the treshold values and populate them into structur for save (e.g database)
	 * @return <code>true</code> if values were saved properly, <code>false</code> otherwise
	 */
	public boolean saveTresholdValues();
}

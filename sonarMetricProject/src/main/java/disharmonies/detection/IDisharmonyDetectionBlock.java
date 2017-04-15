/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import java.util.Map;

import main.java.tresholds.IThresholds;

/**
 * Interface of the detection block which can be for instance a metric or a binary operator aggregating some metrics
 * @author Tomas Lestyan
 */
@FunctionalInterface
public interface IDisharmonyDetectionBlock {

	/**
	 * Evaluate the detection block
	 * @param measures
	 * @param tresholds
	 * @return <code>true</code> if disharmony detectedion succeded, <code>false</code> otherwise
	 */
	public boolean evaluate(Map<String, Integer> measures, IThresholds tresholds);
}

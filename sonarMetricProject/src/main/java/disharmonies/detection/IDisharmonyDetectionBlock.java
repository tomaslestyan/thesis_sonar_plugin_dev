/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import org.sonar.plugins.java.api.tree.Tree;

import main.java.tresholds.ITresholds;

/**
 * Interface of the detection block which can be for instance a metric or a binary operator aggregating some metrics
 * @author Tomas Lestyan
 */
@FunctionalInterface
public interface IDisharmonyDetectionBlock {

	/**
	 * Evaluate the detection block
	 * @param tree
	 * @param tresholds
	 * @return <code>true</code> if disharmony detectedion succeded, <code>false</code> otherwise
	 */
	public boolean evaluate(Tree tree, ITresholds tresholds);
}

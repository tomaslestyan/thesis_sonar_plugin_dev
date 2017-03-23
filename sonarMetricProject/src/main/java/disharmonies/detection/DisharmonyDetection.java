/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sonar.plugins.java.api.tree.Tree;

import main.java.tresholds.ITresholds;

/**
 * Detection of the disharmonies according to given metrics
 * @author Tomas Lestyan
 */
@XmlRootElement (name = "disharmonyDetection")
public class DisharmonyDetection {

	@XmlAnyElement(lax = true)
	protected List<Object> children;

	/**
	 * Detect if given tree has a disharmony
	 * @param tree the tree which will be visited
	 * @param tresholds the treshold values
	 * @return <code>true</code> if disharmony is detected, <code>false</code> otherwise
	 */
	public <T> boolean disharmonyDetected(Tree tree, ITresholds tresholds) {
		boolean result = false;
		if (children.size() != 1) {
			throw new IllegalArgumentException("Disharmony detection expects exactly one child tag from following ones : and, or, condition");
		}
		for (Object childObject: children) {
			@SuppressWarnings("unchecked")
			T value = ((JAXBElement<T>) childObject).getValue();
			if (value instanceof IDisharmonyDetectionBlock) {
				IDisharmonyDetectionBlock base = (IDisharmonyDetectionBlock) value;
				result = base.evaluate(tree, tresholds);
			}
		}
		return result;
	}

}

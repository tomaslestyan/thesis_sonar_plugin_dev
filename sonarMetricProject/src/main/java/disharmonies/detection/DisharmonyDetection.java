/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.tresholds.IThresholds;

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
	 * @param measures the tree which will be visited
	 * @param tresholds the treshold values
	 * @return <code>true</code> if disharmony is detected, <code>false</code> otherwise
	 */
	public <T> boolean disharmonyDetected(Map<String, Integer> measures, IThresholds tresholds) {
		boolean result = false;
		if (children.size() != 1) {
			throw new IllegalArgumentException("Disharmony detection expects exactly one child tag from following ones : and, or, condition");
		}
		for (Object childObject: children) {
			@SuppressWarnings("unchecked")
			T value = ((JAXBElement<T>) childObject).getValue();
			if (value instanceof IDisharmonyDetectionBlock) {
				IDisharmonyDetectionBlock base = (IDisharmonyDetectionBlock) value;
				result = base.evaluate(measures, tresholds);
			}
		}
		return result;
	}

}

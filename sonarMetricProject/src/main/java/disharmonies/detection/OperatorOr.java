/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import main.java.tresholds.IThresholds;

/**
 * Apperator representing n-ary OR operation
 * @author Tomas Lestyan
 */
@XmlRootElement(name="or")
public class OperatorOr extends ABinaryOperator {

	/* (non-Javadoc)
	 * @see main.java.disharmonies.detection.ABinaryOperator#evaluate(org.sonar.plugins.java.api.tree.Tree, main.java.tresholds.Tresholds)
	 */
	@Override
	public boolean evaluate(Map<String, Integer> measures, IThresholds tresholds) {
		boolean result = true;
		for (IDisharmonyDetectionBlock block : getChildren()) {
			result = block.evaluate(measures, tresholds) || result;
		}
		return result;
	}

}

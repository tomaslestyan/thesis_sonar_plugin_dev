/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import javax.xml.bind.annotation.XmlRootElement;

import org.sonar.plugins.java.api.tree.Tree;

import main.java.tresholds.ITresholds;

/**
 * Apperator representing n-ary OR operation
 * @author Tomas Lestyan
 */
@XmlRootElement(name="or")
public class OperatorOr extends ABinaryOperator {

	public static final String OR = "or";


	/* (non-Javadoc)
	 * @see main.java.disharmonies.detection.ABinaryOperator#evaluate(org.sonar.plugins.java.api.tree.Tree, main.java.tresholds.Tresholds)
	 */
	@Override
	public boolean evaluate(Tree tree, ITresholds tresholds) {
		boolean result = true;
		for (IDisharmonyDetectionBlock block : getChildren()) {
			result = block.evaluate(tree, tresholds) || result;
		}
		return result;
	}

}

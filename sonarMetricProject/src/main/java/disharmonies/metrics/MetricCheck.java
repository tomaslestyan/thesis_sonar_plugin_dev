package main.java.disharmonies.metrics;

import org.sonar.plugins.java.api.tree.Tree;

import main.java.tresholds.ITresholds;
import main.java.tresholds.PercentileSemantics;
import main.java.visitors.ADisharmonyVisitor;

/**
 * Metric used for checks in code
 * @author Tomas Lestyan
 */
public class MetricCheck extends AMetric{

	private final Tree tree;
	private final ITresholds tresholds;
	private final ADisharmonyVisitor visitor;

	/**
	 * Constructor
	 * @param tree
	 * @param tresholds
	 * @param visitor
	 */
	public MetricCheck(Tree tree, ITresholds tresholds, ADisharmonyVisitor visitor) {
		this.tree = tree;
		this.tresholds = tresholds;
		this.visitor = visitor;
	}

	/**
	 * Evaluate the metric
	 * @param operator
	 * @param value
	 * @return <code>true</code> if anomaly found, <code>false</code> otherwise
	 */
	public boolean evaluate(CompareOperator operator, int value) {
		return compareToTreshold(getResult(tree, visitor), value, operator);
	}

	/**
	 * Evaluate the metric
	 * @param operator
	 * @param semantics
	 * @return <code>true</code> if anomaly found, <code>false</code> otherwise
	 */
	public boolean evaluate(CompareOperator operator, PercentileSemantics semantics, String modifyExpr) {
		Modifier modifier = new Modifier(modifyExpr);
		int tresholdValue = modifier.modify(tresholds.getTresholdValueOf(visitor.getKey(), semantics.getValue()));
		return evaluate(operator, tresholdValue);
	}

	/**
	 * Evaluate the metric
	 * @param operator
	 * @param semantics
	 * @return <code>true</code> if anomaly found, <code>false</code> otherwise
	 */
	public boolean evaluate(CompareOperator operator, PercentileSemantics semantics) {
		return evaluate(operator, semantics, null);
	}

}

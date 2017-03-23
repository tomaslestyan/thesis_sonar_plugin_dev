package main.java.disharmonies.metrics;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.tree.Tree;

import main.java.disharmonies.detection.IDisharmonyDetectionBlock;
import main.java.plugin.DisharmoniesContextSingleton;
import main.java.tresholds.ITresholds;
import main.java.tresholds.PercentileSemantics;
import main.java.visitors.ADisharmonyVisitor;

/**
 * Metric parsed from XML definition
 * @author Tomas Lestyan
 */
@XmlRootElement (name = "metric")
public class XmlMetric extends AMetric implements IDisharmonyDetectionBlock {

	/** The logger object */
    Logger log = LoggerFactory.getLogger(this.getClass());

	@XmlAttribute
	private String name;
	@XmlAttribute
	private String compare;
	@XmlAttribute
	private String value;
	@XmlAttribute
	private String semantic;
	@XmlAttribute
	private String modifier;
	private static final DisharmoniesContextSingleton context  = DisharmoniesContextSingleton.getInstance();

	/* (non-Javadoc)
	 * @see main.java.disharmonies.detection.IDisharmonyDetectionBlock#evaluate(org.sonar.plugins.java.api.tree.Tree, main.java.tresholds.Tresholds)
	 */
	@Override
	public boolean evaluate(Tree tree, ITresholds tresholds) {
		ADisharmonyVisitor visitor = context.getVisitor(name);
		int result = getResult(tree, visitor);
		int treshold = getTresholdValue(tresholds, visitor);
		tresholds.saveTresholdValue(tree, visitor.getKey(), result);
		return compareToTreshold(result, treshold, CompareOperator.getOperator(compare));
	}

	/**
	 * Get the treshold value from value defined in XML
	 * @param tresholds
	 * @return the value of the treshold
	 */
	public int getTresholdValue(ITresholds tresholds, ADisharmonyVisitor visitor) {
		Modifier m = new Modifier(modifier);
		PercentileSemantics percentileSemantic = PercentileSemantics.getSemantic(semantic);
		int tresholdValue = 0;
		if (percentileSemantic.equals(PercentileSemantics.NOT_VALID)) {
			// using semantic variables e.g HIGH, LOW, AVERAGE ...
			tresholdValue = tresholds.getTresholdValueOf(visitor.getKey(), percentileSemantic.getValue());
		} else if (value != null) {
			// using given value
			try {
				tresholdValue =Integer.parseInt(value);
			} catch (NumberFormatException e) {
				log.warn(value + "is not valid integer");
			}
		} else {
			// using the default treshold value
			// WARNING this can be different in each treshold implementation
			// do not use
			tresholds.getTresholdValueOf(visitor.getKey());
		}
		return m.modify(tresholdValue);
	}
}

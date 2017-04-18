package main.java.disharmonies.detection;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.tresholds.IThresholds;
import main.java.tresholds.PercentileSemantics;

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

	/**
	 * Get the threshold value from value defined in XML
	 * @param tresholds
	 * @param metricID 
	 * @return the value of the threshold
	 */
	public int getTresholdValue(IThresholds tresholds, String metricID) {
		Modifier m = new Modifier(modifier);
		PercentileSemantics percentileSemantic = PercentileSemantics.getSemantic(semantic);
		int tresholdValue = 0;
		if (!percentileSemantic.equals(PercentileSemantics.NOT_VALID)) {
			// using semantic variables e.g HIGH, LOW, AVERAGE ...
			tresholdValue = tresholds.getTresholdValueOf(metricID, percentileSemantic.getValue());
		} else if (value != null) {
			// using given value
			try {
				tresholdValue =Integer.parseInt(value);
			} catch (NumberFormatException e) {
				log.warn(value + "is not valid integer");
			}
		} else {
			// using the default threshold value
			// WARNING this can be different in each threshold implementation
			// do not use
			tresholds.getTresholdValueOf(metricID);
		}
		return m.modify(tresholdValue);
	}

	/* (non-Javadoc)
	 * @see main.java.disharmonies.detection.IDisharmonyDetectionBlock#evaluate(java.util.Map, main.java.tresholds.IThresholds)
	 */
	@Override
	public boolean evaluate(Map<String, Integer> measures, IThresholds tresholds) {
		int result = measures.getOrDefault(name, -1).intValue();
		int treshold = getTresholdValue(tresholds, name);
		return compareToTreshold(result, treshold, CompareOperator.getOperator(compare));
	}
}
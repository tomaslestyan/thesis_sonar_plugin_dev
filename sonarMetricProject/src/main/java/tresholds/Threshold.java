package main.java.tresholds;

import java.util.HashMap;
import java.util.Map;

public class Threshold {


	private final String metricID;
	private final static Integer DEFAULT_VALUE = Integer.valueOf(-1);
	private final Map<String, Integer> metricTresholds;
	/**
	 * Constructor
	 * @param metricID
	 */
	Threshold(String metricID, Map<String, Integer> metricTresholds) {
		this.metricID = metricID;
		this.metricTresholds = metricTresholds;
	}

	public String getMetricID() {
		return metricID;
	}

	public int getThresholdValue(String semantic) {
		return metricTresholds.getOrDefault(semantic, DEFAULT_VALUE);
	}

	public int getAverageThresholdValue() {
		return metricTresholds.getOrDefault(PercentileSemantics.AVERAGE, DEFAULT_VALUE);
	}

	public static Builder getBuilder(String metricID) {
		return new Builder(metricID);
	}


	/**
	 * TODO
	 * @author Tomas
	 */
	public static class Builder {
		private String metricID;
		private Map<String, Integer> metricTresholds = new HashMap<>();

		/**
		 * Constructor
		 * @param metricID
		 */
		public Builder(String metricID) {
			this.metricID = metricID;
		}

		/**
		 * @param semantics
		 * @param value
		 * @return
		 */
		public Builder addValue(String semantics, int value) {
			metricTresholds.put(semantics, Integer.valueOf(value));
			return this;
		}

		public Threshold build() {
			return new Threshold(metricID, metricTresholds);
		}
	}

}

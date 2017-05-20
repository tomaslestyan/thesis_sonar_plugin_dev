/**
 *
 */
package main.java.tresholds;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import main.java.disharmonies.parser.Semantic;
import main.java.framework.api.metrics.MetricsRegister;
import main.java.tresholds.Threshold.Builder;

/**
 * @author Tomas Lestyan
 */
public class StaticThresholds implements IThresholds {

	/** The singleton instance*/
	private static volatile StaticThresholds INSTANCE;

	private Map<String, Threshold> thresholds = new HashMap<>();

	/**
	 * TODO
	 * @return
	 */
	public static StaticThresholds getInstance() {
		if (INSTANCE  != null ) return INSTANCE;
		synchronized (StaticThresholds.class) {
			if (INSTANCE == null ) {
				INSTANCE = new StaticThresholds();
			}
		}
		return INSTANCE;
	}


	/* (non-Javadoc)
	 * @see main.java.tresholds.IThresholds#getTresholdValueOf(java.lang.String, main.java.tresholds.PercentileSemantics)
	 */
	@Override
	public int getTresholdValueOf(String metricID, String semantics) {
		Threshold threshold = thresholds.get(metricID);
		if (threshold != null) {
			return threshold.getThresholdValue(semantics);
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.IThresholds#getTresholdValueOf(java.lang.String, double)
	 */
	@Override
	public int getTresholdValueOf(String metricID, double percentile) {
		// Not supported
		return -1;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.IThresholds#init(java.util.Collection)
	 */
	@Override
	public void init(Collection<Semantic> semantics) {
		MetricsRegister.getFrameworkMetrics().forEach(m -> {
			String metricKey = m.getKey();
			Builder thresholdbuilder = Threshold.getBuilder(metricKey);
			semantics.forEach(s -> {
				thresholdbuilder.addValue(s.getSemantic(), s.getValueAsInt());
			});
			thresholds.put(metricKey, thresholdbuilder.build());
		});

	}

}

/**
 *
 */
package main.java.tresholds;

import static main.java.tresholds.TresholdRegister.CLASS_LOC_HIGH;
import static main.java.tresholds.TresholdRegister.CYCLO_HIGH;
import static main.java.tresholds.TresholdRegister.MAXNESTING_SEVERAL;
import static main.java.tresholds.TresholdRegister.NOAV_MANY;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * @author Tomas Lestyan
 */
public class StaticThresholds implements IThresholds {

	/** The singleton instance*/
	private static volatile StaticThresholds INSTANCE;

	private final Map<String, Integer> tresholds = ImmutableMap.<String, Integer>builder().
			put(CLASS_LOC_HIGH, 50).
			put(CYCLO_HIGH, 10).
			put(MAXNESTING_SEVERAL, 3).
			put(NOAV_MANY, 5).
			build();

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

	private StaticThresholds() {
		// Do not allow to create instances
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.Tresholds#getTresholdValueOf(java.lang.String)
	 */
	@Override
	public int getTresholdValueOf(String metricID) {
		Integer treshold = tresholds.get(metricID);
		if (treshold != null) {
			return treshold.intValue();
		}
		return -1;
	}

	@Override
	public int getTresholdValueOf(String key, double percentile) {
		return getTresholdValueOf(key);
	}

}

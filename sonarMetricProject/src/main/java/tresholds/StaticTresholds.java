/**
 *
 */
package main.java.tresholds;

import static main.java.tresholds.TresholdRegister.CLASS_LOC_HIGH;
import static main.java.tresholds.TresholdRegister.CYCLO_HIGH;
import static main.java.tresholds.TresholdRegister.MAXNESTING_SEVERAL;
import static main.java.tresholds.TresholdRegister.NOAV_MANY;

import java.util.Map;

import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.collect.ImmutableMap;

/**
 * @author Tomas Lestyan
 */
public class StaticTresholds implements ITresholds {
	private final Map<String, Integer> tresholds = ImmutableMap.<String, Integer>builder().
		      put(CLASS_LOC_HIGH, 50).
		      put(CYCLO_HIGH, 10).
		      put(MAXNESTING_SEVERAL, 3).
		      put(NOAV_MANY, 5).
		      build();

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

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#saveTresholdValues(java.util.Map)
	 */
	@Override
	public boolean saveTresholdValues() {
		return true;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#saveTresholdValue(org.sonar.plugins.java.api.tree.Tree, java.lang.String, int)
	 */
	@Override
	public void saveTresholdValue(Tree tree, String key, int value) {
		// nothing to do
	}

	@Override
	public int getTresholdValueOf(String key, double percentile) {
		return getTresholdValueOf(key);
	}

}

package main.java.tresholds;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.tree.Tree;

import main.java.tresholds.clients.DerbyClient;
import main.java.utils.GenericPair;

/**
 * Tresholds stored in local database
 * @author Tomas Lestyan
 */
public class LocalTresholds implements ITresholds {
	/** The logger object */
    private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final DerbyClient client = new DerbyClient();
	private final Map<GenericPair<Tree, String>, Integer> buffer = new HashMap<>();

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#getTresholdValueOf(java.lang.String)
	 */
	@Override
	public int getTresholdValueOf(String key) {
		return getTresholdValueOf(key, PercentileSemantics.AVERAGE.getValue());
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#getTresholdValueOf(java.lang.String, double)
	 */
	@Override
	public int getTresholdValueOf(String key, double percentile) {
		return client.getMetricValue(key, percentile);
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#saveTresholdValue(org.sonar.plugins.java.api.tree.Tree, java.lang.String, int)
	 */
	@Override
	public void saveTresholdValue(Tree tree, String key, int value) {
		GenericPair<Tree, String> keyPair = new GenericPair<>(tree, key);
		if (buffer.containsKey(keyPair)) {
			Integer storedValue = buffer.get(keyPair);
			if (storedValue.intValue() != value) {
				log.warn("Different values computed for metric : " + key + "(" + storedValue + ", " + value + ")");
			}
		}
		buffer.put(keyPair, Integer.valueOf(value));
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#saveTresholdValues()
	 */
	@Override
	public boolean saveTresholdValues() {
		buffer.forEach((k, v) -> client.saveMetricValue(k.getRight(), v));
		buffer.clear();
		return true;
	}

}

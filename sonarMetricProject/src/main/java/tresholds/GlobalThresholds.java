/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.framework.api.Database;
import main.java.framework.api.metrics.MetricsRegister;

/**
 * TODO
 * @author Tomas Lestyan
 */
public class GlobalThresholds implements IThresholds {

	/** The singleton instance*/
	private static volatile GlobalThresholds INSTANCE;
	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/** Sorted measures grouped by their parent metrics */
	Map<String, List<Integer>> sortedMeasures;

	/**
	 * TODO
	 * @return
	 */
	public static GlobalThresholds getInstance() {
		if (INSTANCE  != null ) return INSTANCE;
		synchronized (GlobalThresholds.class) {
			if (INSTANCE == null ) {
				INSTANCE = new GlobalThresholds();
			}
		}
		return INSTANCE;
	}

	private GlobalThresholds() {
		long timeBefore = System.currentTimeMillis();
		Map<String, List<Integer>> measures = Database.getMeasures(MetricsRegister.getFrameworkMetrics().stream().map(x -> x.getKey()).collect(Collectors.toList()));
		this.sortedMeasures = getSortedMeasures(measures);
		long timeAfter = System.currentTimeMillis();
		log.info(String.format("Retrieving tresholds from DB taken %d ms", timeBefore - timeAfter ));
	}

	private Map<String, List<Integer>> getSortedMeasures(Map<String, List<Integer>> measures) {
		Map<String, List<Integer>> result = new HashMap<>();
		measures.forEach((k, v) -> {
			Collections.sort(v);
			result.put(k, v);
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.Tresholds#getTresholdValueOf(java.lang.String)
	 */
	@Override
	public int getTresholdValueOf(String metricID) {
		return getTresholdValueOf(metricID, PercentileSemantics.AVERAGE.getValue()); 
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#getTresholdValueOf(java.lang.String, double)
	 */
	@Override
	public int getTresholdValueOf(String metricID, double percentile) {
		List<Integer> measures = sortedMeasures.get(metricID);
		if ((measures != null) && !measures.isEmpty()) {
			int index = (int) (measures.size() * percentile);
			return measures.get(index);
		}
		return -1;
	}
}

/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.framework.api.MeasurementRepository;
import main.java.framework.api.metrics.MetricsRegister;
import main.java.parser.Semantic;
import main.java.tresholds.Threshold.Builder;

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
	private Map<String, Threshold> thresholds = new HashMap<>();

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

	/**
	 * Constructor
	 */
	private GlobalThresholds() {
		Map<String, List<Integer>> measures = MeasurementRepository
				.getMeasures(MetricsRegister.getFrameworkMetrics().stream()
						.map(x -> x.getKey())
						.collect(Collectors.toList()));
		this.sortedMeasures = getSortedMeasures(measures);
	}

	/**
	 * @param measures
	 * @return
	 */
	private Map<String, List<Integer>> getSortedMeasures(Map<String, List<Integer>> measures) {
		Map<String, List<Integer>> result = new HashMap<>();
		measures.forEach((k, v) -> {
			Collections.sort(v);
			result.put(k, v);
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#getTresholdValueOf(java.lang.String, double)
	 */
	@Override
	public int getTresholdValueOf(String metricID, double percentile) {
		if (percentile >=  0.00 ) {
			List<Integer> measures = sortedMeasures.get(metricID);
			if ((measures != null) && !measures.isEmpty()) {
				int index = (int) (measures.size() * percentile);
				return measures.get(index);
			}
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.IThresholds#getTresholdValueOf(java.lang.String, java.lang.String)
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
	 * @see main.java.tresholds.IThresholds#init(java.util.Collection)
	 */
	@Override
	public void init(Collection<Semantic> semantics) {
		MetricsRegister.getFrameworkMetrics().forEach(m -> {
			String metricKey = m.getKey();
			Builder thresholdbuilder = Threshold.getBuilder(metricKey);
			semantics.forEach(s -> {
				thresholdbuilder.addValue(s.getSemantic(), getTresholdValueOf(metricKey, s.getPercentileAsDouble()));
			});
			thresholds.put(metricKey, thresholdbuilder.build());
		});
	}
}

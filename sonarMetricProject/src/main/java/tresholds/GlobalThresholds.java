/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

import static main.java.tresholds.TresholdRegister.CLASS_LOC_HIGH;
import static main.java.tresholds.TresholdRegister.CYCLO_HIGH;
import static main.java.tresholds.TresholdRegister.MAXNESTING_SEVERAL;
import static main.java.tresholds.TresholdRegister.NOAV_MANY;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.collect.ImmutableMap;

import main.java.tresholds.clients.HttpClient;
import main.java.utils.JsonUtils;

/**
 * TODO
 * @author Tomas Lestyan
 */
public class GlobalThresholds implements IThresholds {

	HttpClient client = new HttpClient();
	Collection<String> tresholdBuffer = new ArrayList<>();

	/* (non-Javadoc)
	 * @see main.java.tresholds.Tresholds#getTresholdValueOf(java.lang.String)
	 */
	@Override
	public int getTresholdValueOf(String metricID) {
		//TODO just for test, needs to be implemented
		Map<String, Integer> tresholds = ImmutableMap.<String, Integer>builder().
			      put(CLASS_LOC_HIGH, 50).
			      put(CYCLO_HIGH, 10).
			      put(MAXNESTING_SEVERAL, 3).
			      put(NOAV_MANY, 5).
			      build();
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
		boolean saved = client.postValue(JsonUtils.createJsonArray(tresholdBuffer));
		if (saved) {
			tresholdBuffer.clear();
		}
		return saved;
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#saveTresholdValue(org.sonar.plugins.java.api.tree.Tree, java.lang.String, int)
	 */
	@Override
	public void saveTresholdValue(Tree tree, String key, int value) {
//		tresholdBuffer.add(JsonUtils.createMetricValuePostJson(key, value));
	}

	/* (non-Javadoc)
	 * @see main.java.tresholds.ITresholds#getTresholdValueOf(java.lang.String, double)
	 */
	@Override
	public int getTresholdValueOf(String key, double percentile) {
//		return client.getValue(JsonUtils.createMetricValueGetJson(key, percentile));
		return -1;
	}
}

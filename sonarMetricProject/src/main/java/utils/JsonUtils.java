/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.utils;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * TODO
 * @author Tomas Lestyan
 */
public class JsonUtils {

	//	@SuppressWarnings("unchecked")
	//	public static String createMetricDefinitionJson(String metricID, String name, String description) {
	//		JSONObject obj = new JSONObject();
	//		obj.put(TextUtils.ID, metricID);
	//		obj.put(TextUtils.NAME, name);
	//		obj.put(TextUtils.DESCRIPTION, description);
	//		return obj.toJSONString();
	//	}
	@SuppressWarnings("unchecked")
	public static String createMetricValuePostJson(int id, int value) {
		JSONObject obj = new JSONObject();
		obj.put(TextUtils.METRIC_ID, id);
		obj.put(TextUtils.VALUE, Integer.toString(value));
		//		return String.format("{%s:%s, %s:%s}", TextUtils.METRIC_ID, id, TextUtils.VALUE, value);
		return obj.toJSONString();
	}
	@SuppressWarnings("unchecked")
	public static String createMetricValueGetJson(int id, double tresholdPercentile) {
		JSONObject obj = new JSONObject();
		obj.put(TextUtils.METRIC_ID, id);
		obj.put(TextUtils.TRESHOLD_PERCENTILE,Double.toString(tresholdPercentile));
		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public static String createJsonArray(Collection<String> objects) {
		StringJoiner sj = new StringJoiner(", ");
		objects.forEach(x -> sj.add(x));
		JSONArray array = new JSONArray();
		array.addAll(objects);
		return String.format("$data = %s", array.toJSONString());
		//		return String.format("$data = [%s]", sj.toString());
	}

	public static String createMetricJson(String metricID, Map<String, String> values) {
		return null;
	}
}

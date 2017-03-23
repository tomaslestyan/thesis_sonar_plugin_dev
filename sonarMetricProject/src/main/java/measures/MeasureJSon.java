/**
 * 
 */
package main.java.measures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author Tomas
 *
 */
public class MeasureJSon extends  JSONObject {

	final static String METRIC = "metric";
	final static String VALUES = "values";
	private String metricID;
	private JSONArray methodMeasures = new JSONArray();


	/**
	 * @return
	 */
	public String getMetricID() {
		return metricID;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection<MethodMeasureJson> getMethodMeasures() {
		List<MethodMeasureJson> result = new ArrayList<>();
		methodMeasures.stream()
		.filter(x -> x instanceof MethodMeasureJson)
		.forEachOrdered(x -> result.add((MethodMeasureJson) x));
		return result;
	}

	/**
	 * @param metricID
	 * @param values
	 */
	@SuppressWarnings("unchecked")
	public MeasureJSon(String metricID, JSONArray methodMeasures) {
		this.metricID = metricID;
		this.methodMeasures = methodMeasures;
		put(METRIC, metricID);
		put(VALUES, methodMeasures);
	}

	/* (non-Javadoc)
	 * @see com.google.gson.JsonElement#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}

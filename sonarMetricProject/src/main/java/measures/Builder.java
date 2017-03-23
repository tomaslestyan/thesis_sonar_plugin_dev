/**
 * 
 */
package main.java.measures;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author Tomas
 *
 */
public class Builder {
	private JSONArray methodMeasures = new JSONArray();
	private String metricID;

	@SuppressWarnings("unchecked")
	public void addMeasureForMetod(String methodID, int startLine, int endLine, String value) {
		methodMeasures.add(new MethodMeasureJson(methodID, startLine, endLine, value).toJSONString());
	}

	public void forMetric(String metricID) {
		this.metricID = metricID;
	}

	public void fromString(String jsonValue) {
		JSONParser parser = new JSONParser();
		Object json;
		try {
			json = parser.parse(jsonValue);
			if (json instanceof JSONObject) {
				this.metricID = ((JSONObject) json).get(MeasureJSon.METRIC).toString();
				Object values = ((JSONObject) json).get(MeasureJSon.VALUES);
				if (values instanceof JSONArray) {
					this.methodMeasures = (JSONArray) values;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MeasureJSon build() {
		assert metricID != null;
		return new MeasureJSon(metricID, methodMeasures);
	}
}
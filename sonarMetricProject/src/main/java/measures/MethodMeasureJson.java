package main.java.measures;

import org.json.simple.JSONObject;

public class MethodMeasureJson extends JSONObject {

	final static String METHOD = "method";
	final static String START = "start";
	final static String END = "end";
	final static String VALUE = "value";
	public static final long serialVersionUID = 1L;
	String methodID; 
	int startLine; 
	int endLine; 
	String value;

	/**
	 * @param methodID
	 * @param startLine
	 * @param endLine
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public MethodMeasureJson(String methodID, int startLine, int endLine, String value) {
		this.methodID = methodID;
		this.startLine = startLine;
		this.endLine = endLine;
		this.value = value;

		put(METHOD, methodID);
		put(START, Integer.toString(startLine));
		put(END, Integer.toString(endLine));
		put(VALUE, value);
	}

	public String getMethodID() {
		return methodID;
	}

	public int getStartLine() {
		return startLine;
	}

	public int getEndLine() {
		return endLine;
	}

	public String getValue() {
		return value;
	}
}

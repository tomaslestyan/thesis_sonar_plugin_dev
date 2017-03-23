package main.java.tresholds;

/**
 * Semantic values of the treshold percentiles
 * @author Tomas Lestyan
 */
public enum PercentileSemantics {

	 NOT_VALID(0.00), FEW(0.07), LOW(0.25), SEVERAL(0.33), AVERAGE(0.50), MANY(0.66), HIGH(0.75), VERY_HIGH(0.93);

    private double value;

    PercentileSemantics(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    /**
	 * TODO
	 * @param value
	 * @return
	 */
	public static PercentileSemantics getSemantic(String value) {
		if (value == null) {
			return PercentileSemantics.NOT_VALID;
		}
		double doubleValue = 0;
		try {
			doubleValue = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return PercentileSemantics.NOT_VALID;
		}
		return  getSemantic(doubleValue);
	}

	/**
	 * @param doubleValue
	 * @return the semantic from double value
	 */
	public static PercentileSemantics getSemantic(double doubleValue) {
		for(PercentileSemantics semantic : values())
		    if (Double.compare(semantic.getValue(), doubleValue) == 0) {
		    	return semantic;
		    }
		return NOT_VALID;
	}

}

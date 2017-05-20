package main.java.tresholds;

/**
 * Semantic values of the treshold percentiles
 * @author Tomas Lestyan
 */
public enum PercentileSemantics {

	UNKNOWN(-1.00, "unknown"),
	ZERO(0.00, "zero"),
	FEW(0.07, "few"),
	LOW(0.25, "low"),
	ONE_QUARTER(0.25, "one_quarter"),
	SEVERAL(0.33, "several"),
	AVERAGE(0.50, "average"),
	MANY(0.66, "many"),
	HIGH(0.75, "high"),
	VERY_HIGH(0.93, "veryhigh"),
	SHALLOW(0.33, "shallow"), 
	ONE_THIRD(0.33, "one_third"), 
	HALF(0.50, "half"), 
	TWO_THIRDS(0.66, "two_thirds"), 
	THREE_QUARTERS(0.75, "three_quarters");

	private double value;
	private String name;

	PercentileSemantics(double value, String name) {
		this.value = value;
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * TODO
	 * @param name 
	 * @return
	 */
	public static PercentileSemantics getSemantic(String name) {
		if (name != null) {			
			for (PercentileSemantics semantic : values()) {
				if (semantic.getName().equalsIgnoreCase(name)) {
					return semantic;
				}
			}
			return getSemantic(Double.parseDouble(name));
		}
		return UNKNOWN;
	}

	/**
	 * @param doubleValue
	 * @return the semantic from double value
	 */
	public static PercentileSemantics getSemantic(double doubleValue) {
		for (PercentileSemantics semantic : values())
			if (Double.compare(semantic.getValue(), doubleValue) == 0) {
				return semantic;
			}
		return UNKNOWN;
	}

}

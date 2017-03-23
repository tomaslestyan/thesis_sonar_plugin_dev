/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

/**
 * @author Tomas Lestyan
 */
@Deprecated
public class TresholdRegister {

	/**
	 * Constructor
	 */
	private TresholdRegister() {
		//do not allow to create instances
	}

	public final  static String CLASS_LOC_HIGH = "class_linesOfCode_high";
	public final  static String CYCLO_HIGH = "method_cyclomaticCompelxity_high";
	public final  static String MAXNESTING_SEVERAL = "method_maxNesting_high";
	public final  static String NOAV_MANY = "method_numberOfVariablesHigh";
}

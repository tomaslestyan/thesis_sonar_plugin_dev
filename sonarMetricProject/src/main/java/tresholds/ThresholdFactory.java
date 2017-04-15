/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

/**
 * Factory to get the treshold holder object
 * @author Tomas Lestyan
 */
public class ThresholdFactory {

	private static String whichTresholdsToUse = "static"; //not implemented yet
	private static  StaticThresholds STATIC_TRESHOLDS= new StaticThresholds();

	/**
	 * Constructor
	 */
	private ThresholdFactory() {
		// do not allow create instances
	}

	public static IThresholds getTresholds() {
		//TODO
		return STATIC_TRESHOLDS;
	}
}

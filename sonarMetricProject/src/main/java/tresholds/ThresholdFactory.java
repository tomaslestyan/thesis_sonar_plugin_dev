/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

/**
 * Factory to get the threshold holder object
 * @author Tomas Lestyan
 */
public class ThresholdFactory {

	private static String whichThresholdsToUse = "global"; //not implemented yet

	/**
	 * Constructor
	 */
	private ThresholdFactory() {
		// do not allow create instances
	}

	public static IThresholds getThresholds() {
		//TODO
		return GlobalThresholds.getInstance();
	}
}

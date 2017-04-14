/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

/**
 * Factory to get the treshold holder object
 * @author Tomas Lestyan
 */
public class TresholdFactory {

	private static String whichTresholdsToUse = "static"; //not implemented yet
	private static  StaticTresholds STATIC_TRESHOLDS= new StaticTresholds();

	/**
	 * Constructor
	 */
	private TresholdFactory() {
		// do not allow create instances
	}

	public static ITresholds getTresholds() {
		//TODO
		return STATIC_TRESHOLDS;
	}
}

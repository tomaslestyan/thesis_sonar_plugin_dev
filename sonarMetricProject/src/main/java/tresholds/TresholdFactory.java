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
	private static  GlobalTresholds GLOBAL_TRESHOLDS= new GlobalTresholds();
	private static  LocalTresholds LOCAL_TRESHOLDS= new LocalTresholds();
	private static  StaticTresholds STATIC_TRESHOLDS= new StaticTresholds();

	/**
	 * Constructor
	 */
	private TresholdFactory() {
		// do not allow create instances
	}

	public static ITresholds getTresholds() {
		if ("global".equals(whichTresholdsToUse)) {
			return GLOBAL_TRESHOLDS;
		} else if ("local".equals(whichTresholdsToUse)) {
			return LOCAL_TRESHOLDS;
		}
		return STATIC_TRESHOLDS;
	}
}

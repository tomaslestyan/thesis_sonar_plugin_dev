/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.tresholds;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.parser.ThresholdParser;
import main.java.parser.ThresholdsRepository;
import main.java.plugin.DisharmoniesPlugin;

/**
 * Factory to get the threshold holder object
 * @author Tomas Lestyan
 */
public class ThresholdFactory {

	/** The logger object */
	private static Logger LOG = LoggerFactory.getLogger(ThresholdFactory.class);
	private static ThresholdsRepository thresholdRepository;

	static {
		try (InputStream is = DisharmoniesPlugin.THRESHOLDS_URL.openStream()) {
			thresholdRepository = ThresholdParser.parse(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Constructor
	 */
	private ThresholdFactory() {
		// do not allow to create instances
	}

	/**
	 * @return singleton instance of the {@link IThresholds} class
	 */
	public static IThresholds getThresholds() {
		IThresholds thresholds = null;
		String type = thresholdRepository.getType();
		switch (type) {
		case "statistical":
			thresholds = GlobalThresholds.getInstance();
			break;
		case "static":
			thresholds = StaticThresholds.getInstance();
			break;
		default:
			LOG.warn("Not valid threshold type, only statistical and static is allowed. Statistical thresholds will be used");
			thresholds = GlobalThresholds.getInstance();
			break;
		}
		// init the thresholds
		thresholds.init(thresholdRepository.getSemantics());
		return thresholds;
	}
}

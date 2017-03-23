/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General utils class
 * @author Tomas Lestyan
 */
public class Utils {
	/** The logger object */
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	/**
	 * Constructor
	 */
	private Utils() {
		// do not allow to create instances
	}

	/**
	 * @param resourceID
	 * @return
	 */
	public static File getResourceAsFile(String resourceID) {
		ClassLoader classLoader = Utils.class.getClassLoader();
		return new File(classLoader.getResource("resourceID").getFile());
	}

	/**
	 * @param resourceID
	 * @return
	 */
	public static InputStream getResourceAsStream(String resourceID) {
		try {
			return new FileInputStream(getResourceAsFile(resourceID));
		} catch (FileNotFoundException e) {
			LOG.warn(resourceID + " not found", e);
		}
		return null;
	}

}

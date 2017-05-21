/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.parser;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomas Lestyan
 */
public class ThresholdParser {

	/** The logger of the class */
	private final static Logger log = LoggerFactory.getLogger(ThresholdParser.class);

	/**
	 * Constructor
	 */
	private ThresholdParser () {
		// do not allow create instances of the class containing only static methods
	}


	/**
	 * Parse thresholds from XML file
	 * @param is
	 * @return
	 */
	public static ThresholdsRepository parse(InputStream is) {
		ThresholdsRepository thresholds = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ThresholdsRepository.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			thresholds = (ThresholdsRepository) jaxbUnmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			log.error("Threshold parsing failed", e);
		}
		return thresholds;
	}
}

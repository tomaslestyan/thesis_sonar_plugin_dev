/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Tomas Lestyan
 */
public class ThresholdParser {

	/**
	 * Constructor
	 */
	private ThresholdParser () {
		// do not allow create instances of the class containing only static methods
	}


	public static ThresholdsRepository parse(InputStream is) {
		ThresholdsRepository thresholds = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ThresholdsRepository.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			thresholds = (ThresholdsRepository) jaxbUnmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return thresholds;
	}

	public static void main(String[] args) throws FileNotFoundException {
		parse(new FileInputStream(new File("D:\\developement\\thesis_sonar_plugin_dev\\sonarMetricProject\\src\\resources\\thresholds.xml")));
	}
}

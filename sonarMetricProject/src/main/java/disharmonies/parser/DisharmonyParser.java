/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.disharmonies.detection.DisharmonyDetectionBlockFactory;

/**
 * @author Tomas Lestyan
 */
public class DisharmonyParser {

	/**
	 * Constructor
	 */
	private DisharmonyParser () {
		// do not allow create instances of the class containing only static methods
	}

	/** The logger object */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Parse the disharmonies from input stream
	 * @param is
	 * @return collection of disharmonies
	 */
	public static Collection<Disharmony> parse(InputStream is) {
		XmlRules rules = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlRules.class, DisharmonyDetectionBlockFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rules = (XmlRules) jaxbUnmarshaller.unmarshal(is);
		  } catch (JAXBException e) {
			LoggerFactory.getLogger(DisharmonyParser.class).warn("Parsing failed", e);
		  }
		return (rules != null) ? rules.getDisharmonies() : new ArrayList<>();
	}

}

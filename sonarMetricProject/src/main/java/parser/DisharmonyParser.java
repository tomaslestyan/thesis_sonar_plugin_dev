/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.parser;

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
	private final static Logger log = LoggerFactory.getLogger(DisharmonyParser.class);

	/**
	 * Parse the disharmonies from input stream
	 * @param is
	 * @return collection of disharmonies
	 */
	public static Collection<Disharmony> parse(InputStream is) {
		RulesRepository rules = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(RulesRepository.class, DisharmonyDetectionBlockFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			rules = (RulesRepository) jaxbUnmarshaller.unmarshal(is);
		} catch (JAXBException e) {
			log.warn("Parsing failed", e);
		}
		return (rules != null) ? rules.getDisharmonies() : new ArrayList<>();
	}

}

/**
 * The MIT License (MIT)
 * Copyright (c) 2016 FI MUNI
 */
package main.java.disharmonies.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * @author Tomas
 */
@XmlRootElement (name = "thresholdSemantic")
@XmlAccessorType(XmlAccessType.NONE)
public class Semantic {

	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@XmlAttribute
	private String percentile;
	@XmlAttribute
	private String value;
	@XmlValue
	private String semantic;

	/**
	 * @return the percentile
	 */
	public String getPercentile() {
		return percentile;
	}
	/**
	 * @param percentile the percentile to set
	 */
	public void setPercentile(String percentile) {
		this.percentile = percentile;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return value as int
	 */
	public int getValueAsInt() {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			log.warn("threshold senantic value: " + value + "is not valid!");
			return -1;
		}
	}

	/**
	 * @return value as int
	 */
	public double getPercentileAsDouble() {
		try {
			return Double.parseDouble(percentile);
		} catch (NumberFormatException e) {
			log.warn("threshold senantic percentile: " + percentile + "is not valid!");
			return -1;
		}
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the semantic
	 */
	public String getSemantic() {
		return semantic;
	}
	/**
	 * @param semantic the semantic to set
	 */
	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

}

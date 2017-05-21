/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.parser;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Repository of thresholds configuration
 * @author Tomas Lestyan
 */
@XmlRootElement(name="thresholds")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThresholdsRepository {

	@XmlElement(name = "thresholdSemantic")
	private Collection<Semantic> semantics = null;
	@XmlElement
	private String type;

	/**
	 * @return the semantics
	 */
	public Collection<Semantic> getSemantics() {
		return semantics;
	}

	/**
	 * @param semantics the semantics to set
	 */
	public void setSemantics(Collection<Semantic> semantics) {
		this.semantics = semantics;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Thresholds [type=" + type + "]";
	}
}

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
 * XML rules class
 * @author Tomas Lestyan
 */
@XmlRootElement(name="rules")
@XmlAccessorType(XmlAccessType.FIELD)
public class RulesRepository {

	@XmlElement(name = "rule")
	private Collection<Disharmony> disharmonies = null;

	/**
	 * @return disharmonies
	 */
	public Collection<Disharmony> getDisharmonies() {
		return disharmonies;
	}

	/**
	 * Set disharmonies
	 * @param disharmonies
	 */
	public void setDisharmonies(Collection<Disharmony> disharmonies) {
		this.disharmonies = disharmonies;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XmlRules [disharmonies=" + disharmonies + "]";
	}
}

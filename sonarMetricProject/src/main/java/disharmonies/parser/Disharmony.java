package main.java.disharmonies.parser;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import main.java.disharmonies.IDisharmony;
import main.java.disharmonies.detection.DisharmonyDetection;
import main.java.tresholds.IThresholds;

/**
 * Parsed XML disharmony
 * @author Tomas Lestyan
 */
@XmlRootElement (name = "rule")
@XmlAccessorType(XmlAccessType.FIELD)
public class Disharmony implements IDisharmony{

	@XmlElement
	private String scope;
	@XmlElement
	private String name;
	@XmlElement
	private String key;
	@XmlElement
	private String description;
	@XmlElement(name = "disharmonyDetection")
	private DisharmonyDetection detection;


	/* (non-Javadoc)
	 * @see main.java.disharmonies.IDisharmony#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	/* (non-Javadoc)
	 * @see main.java.disharmonies.IDisharmony#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/* (non-Javadoc)
	 * @see main.java.disharmonies.IDisharmony#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the detection
	 */
	public DisharmonyDetection getDetection() {
		return detection;
	}

	/**
	 * @param detection the detection to set
	 */
	public void setDetection(DisharmonyDetection detection) {
		this.detection = detection;
	}

	/**
	 * Check if the given disharmony is detected on given java tree with usage of given tresholds
	 * @param measures 
	 * @param tresholds
	 * @return <code>true</code> if disharmony detected, <code>false</code> otherwise
	 */
	public boolean disharmonyDetected(Map<String, Integer> measures, IThresholds tresholds) {
		return detection.disharmonyDetected(measures, tresholds);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Disharmony [scope=" + scope + ", name=" + name + ", key=" + key + ", description=" + description + "]";
	}
}

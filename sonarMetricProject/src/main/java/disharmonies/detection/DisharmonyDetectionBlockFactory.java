/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.disharmonies.detection;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * Factory for parsing of and, or and metric tags to coresponding objects
 * @author Tomas Lestyan
 */
@XmlRegistry
public class DisharmonyDetectionBlockFactory {

	/**
	 * @param and
	 * @return object of the "and" tag
	 */
	@XmlElementDecl(name="and")
	public JAXBElement<OperatorAnd> createAnd(OperatorAnd and) {
		return new JAXBElement<>(new QName("and"), OperatorAnd.class, and);
	}

	/**
	 * @param or
	 * @return object of the "or" tag
	 */
	@XmlElementDecl(name="or")
	public JAXBElement<OperatorOr> createOr(OperatorOr or) {
		return new JAXBElement<>(new QName("or"), OperatorOr.class, or);
	}

	/**
	 * @param cond
	 * @return the metric object
	 */
	@XmlElementDecl(name="metric")
	public JAXBElement<XmlMetric> createCondition(XmlMetric cond) {
		return new JAXBElement<>(new QName("metric"), XmlMetric.class, cond);
	}

}

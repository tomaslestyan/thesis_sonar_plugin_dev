/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.plugin;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import main.java.disharmonies.parser.Disharmony;

/**
 * Singleton for holding the context of the disharmonies checking plugin
 * @author Tomas Lestyan
 */
public class DisharmoniesContextSingleton {

	/** The singleton instance*/
	private static volatile DisharmoniesContextSingleton INSTANCE;
	/** TODO */
	private final Map<String, Disharmony> rules = new HashMap<>();
	/** TODO */
	private URL xmlRulesLocation;

	/**
	 * TODO
	 * @return
	 */
	public static DisharmoniesContextSingleton getInstance() {
		if (INSTANCE  != null ) return INSTANCE;
		synchronized (DisharmoniesContextSingleton.class) {
			if (INSTANCE == null ) {
				INSTANCE = new DisharmoniesContextSingleton();
			}
		}
		return INSTANCE;
	}

	/**
	 * TODO
	 * @return
	 */
	public Map<String, Disharmony> getRules() {
		return Collections.unmodifiableMap(rules);
	}

	/**
	 * TODO
	 * @param disharmony
	 */
	public void addDisharmonyRule(Disharmony disharmony) {
		rules.put(disharmony.getKey(), disharmony);
	}

	/**
	 * TODO
	 * @param disharmony
	 */
	public void addDisharmonyRules(Collection<Disharmony> disharmonies) {
		disharmonies.forEach(x -> addDisharmonyRule(x));
	}

	/**
	 * @return the xmlRules URL
	 */
	public URL getXmlRulesLocation() {
		return xmlRulesLocation;
	}

	/**
	 * @param xmlRulesLocation the xmlRules URL to set
	 */
	public void setXmlRulesLocation(URL rulesUrl) {
		this.xmlRulesLocation = rulesUrl;
	}
}
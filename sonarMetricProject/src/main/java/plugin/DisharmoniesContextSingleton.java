/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.plugin;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.sonar.plugins.java.api.tree.Tree;

import com.google.common.collect.ImmutableMap;

import main.java.disharmonies.parser.Disharmony;
import main.java.visitors.ADisharmonyVisitor;
import main.java.visitors.ComplexityVisitor;
import main.java.visitors.LinesOfCodeVisitor;
import main.java.visitors.MaxNestingVisitor;
import main.java.visitors.VariableVisitor;

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
	private final Map<Tree, File> fileTrees = new HashMap<>();
	/** TODO */
	private Map<String, ADisharmonyVisitor> visitors;
	private URL xmlRulesLocation;

	/**
	 * Constructor
	 * Not allowing instance creation!
	 */
	private  DisharmoniesContextSingleton() {
		initializeVisitors();
	}


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
	 * TODO
	 * @param tree
	 * @param file
	 */
	public void addTree(Tree tree, File file) {
		fileTrees.put(tree, file);
	}

	/**
	 * TODO
	 * @return
	 */
	public Map<Tree, File> getFileTrees() {
		return Collections.unmodifiableMap(fileTrees);
	}

	/**
	 * @return the visitors
	 */
	public Map<String, ADisharmonyVisitor> getVisitors() {
		return visitors;
	}

	/**
	 * @return the visitors
	 */
	public ADisharmonyVisitor getVisitor(String id) {
		return visitors.get(id);
	}

	/**
	 * TODO
	 */
	private void initializeVisitors() {
		visitors = ImmutableMap.<String, ADisharmonyVisitor> builder()
		.put(LinesOfCodeVisitor.KEY, new LinesOfCodeVisitor())
		.put(MaxNestingVisitor.KEY, new MaxNestingVisitor())
		.put(VariableVisitor.KEY, new VariableVisitor())
		.put(ComplexityVisitor.KEY, new ComplexityVisitor())
		.build();
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
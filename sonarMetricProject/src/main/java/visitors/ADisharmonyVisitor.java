/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.visitors;

import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;

/**
 * Predeccessor od disharmony visitor classes
 * @author Tomas Lestyan
 */
public abstract class ADisharmonyVisitor extends BaseTreeVisitor {

	public abstract String getKey();

	public abstract Scope getScope();

	public abstract int getResult();

	public void scanMethod(MethodTree tree){
		throw new UnsupportedOperationException("Scanning methods with this visitor is not supported");
	}

	public void scanClass(ClassTree tree) {
		throw new UnsupportedOperationException("Scanning classes with this visitor is not supported");
	}

}

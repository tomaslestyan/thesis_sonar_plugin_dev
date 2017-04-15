/**
 * The MIT License (MIT)
 * Copyright (c) 2016 FI MUNI
 */
package main.java.disharmonies;

import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;

/**
 * Interface of disharmony definition
 * @author Tomas Lestyan
 */
public interface IDisharmony extends JavaFileScanner {

	/**
	 * @return the key of the disharmony. Must be unique in repository.
	 */
	public String getKey();

	/**
	 * @return the name of the disharmony
	 */
	public String getName();

	/**
	 * @return the description of the disharmony
	 */
	public String getDescription();

	/* (non-Javadoc)
	 * @see org.sonar.plugins.java.api.JavaFileScanner#scanFile(org.sonar.plugins.java.api.JavaFileScannerContext)
	 */
	@Override
	default void scanFile(JavaFileScannerContext context) {
		// implement this method if needed in implementing classes
	}

}

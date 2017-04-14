package main.java.plugin;

import java.util.Arrays;
import java.util.Collections;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import main.java.disharmonies.BrainMethod;

/**
 * Check for SonarQube
 * @author Tomas Lestyan
 */
public class Checks implements CheckRegistrar {


	/* (non-Javadoc)
	 * @see org.sonar.plugins.java.api.CheckRegistrar#register(org.sonar.plugins.java.api.CheckRegistrar.RegistrarContext)
	 */
	@Override
	public void register(RegistrarContext registrarContext) {
		// Call to registerClassesForRepository to associate the classes with the correct repository key
		registrarContext.registerClassesForRepository(DisharmoniesRules.REPOSITORY, Arrays.asList(checkClasses()), Collections.emptyList());
	}

	/**
	 * @return Lists all the checks provided by the plugin
	 */
	@SuppressWarnings("unchecked")
	public static Class<? extends JavaCheck>[] checkClasses() {
		return new Class [] {
				BrainMethod.class,
				TreeScanner.class,
				FileVisitor.class
		};
	}
}

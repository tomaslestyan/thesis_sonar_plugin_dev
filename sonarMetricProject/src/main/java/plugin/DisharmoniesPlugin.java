package main.java.plugin;


import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.Plugin;

import main.java.framework.db.Configuration;
import main.java.framework.db.DataSourceProvider;

/**
 * The plugin definition
 * TODO under construction - add each new class
 * @author Tomas Lestyan
 */
public class DisharmoniesPlugin implements Plugin {

	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public static final URL RULES_URL = DisharmoniesPlugin.class.getClassLoader().getResource("resources/testrule.xml");



	/**
	 * Constructor
	 */
	public DisharmoniesPlugin() {
		// get database configuration
		Configuration configuration = Configuration.INSTANCE;
		DataSourceProvider.setConfiguration(configuration);
	}

	/* (non-Javadoc)
	 * @see org.sonar.api.Plugin#define(org.sonar.api.Plugin.Context)
	 */
	@Override
	public void define(Context context) {
		context.addExtensions(Checks.class,
				DisharmoniesRules.class,
				DisharmoniesSensor.class);
	}

	public static void main(String[] args) {
		new DisharmoniesPlugin();
	}

}

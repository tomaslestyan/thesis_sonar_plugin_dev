package main.java.plugin;


import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.Plugin;

import main.java.disharmonies.metrics.CustomMetrics;
import main.java.disharmonies.parser.Disharmony;
import main.java.disharmonies.parser.DisharmonyParser;

/**
 * The plugin definition
 * TODO under construction - add each new class
 * @author Tomas Lestyan
 */
public class DisharmoniesPlugin implements Plugin {

	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Constructor
	 */
	public DisharmoniesPlugin() {
		DisharmoniesContextSingleton context = DisharmoniesContextSingleton.getInstance();
		InputStream is = null;
		try {
			// load disharmony rules
			ClassLoader classLoader = getClass().getClassLoader();
			is = classLoader.getResourceAsStream("resources/testrule.xml");
			context.setXmlRulesLocation(classLoader.getResource("resources/testrule.xml"));
			Collection<Disharmony> rules = DisharmonyParser.parse(is);
			context.addDisharmonyRules(rules);
			is.close();
		} catch (IOException e) {
			log.warn("Rules from xml not loaded properly", e);
		}
	}
	/* (non-Javadoc)
	 * @see org.sonar.api.Plugin#define(org.sonar.api.Plugin.Context)
	 */
	@Override
	public void define(Context context) {
		context.addExtensions(Checks.class,
				DisharmoniesRules.class,
				DisharmoniesSensor.class,
				CustomMetrics.class,
				DisharmoniesComputer.class,
				Widget.class);
	}

}

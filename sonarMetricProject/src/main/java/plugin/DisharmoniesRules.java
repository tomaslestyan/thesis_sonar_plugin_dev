/**
 *
 */
package main.java.plugin;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonar.plugins.java.Java;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

/**
 * @author Tomas Lestyan
 */
public class DisharmoniesRules implements RulesDefinition {

	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String REPOSITORY = "disharmoniesRepository";
	public static final RuleKey TEST_RULE_KEY = RuleKey.of(REPOSITORY, "test");
	private final RulesDefinitionXmlLoader xmlLoader;
	private final DisharmoniesContextSingleton contextSingleton  = DisharmoniesContextSingleton.getInstance();

	/**
	 * Constructor
	 * @param xmlLoader
	 */
	public DisharmoniesRules() {
		this.xmlLoader = new RulesDefinitionXmlLoader();
	}

	/* (non-Javadoc)
	 * @see org.sonar.api.server.rule.RulesDefinition#define(org.sonar.api.server.rule.RulesDefinition.Context)
	 */
	@Override
	public void define(Context context) {
		NewRepository repository = context.createRepository(REPOSITORY, "java").setName("Test Rule repository");
		new AnnotationBasedRulesDefinition(repository, Java.KEY).addRuleClasses(false, false, Arrays.asList(Checks.checkClasses()));
		repository.done();
		//		InputStream is = null;
		//		try {
		//			//load rules by sonarqube
		//			is = contextSingleton.getXmlRulesLocation().openStream();
		//			xmlLoader.load(repository, is, Charset.defaultCharset());
		//			is.close();
		//		} catch (IOException e) {
		//			log.warn("Rules from xml not loaded properly", e);
		//		}
		//		repository.done();
	}

}

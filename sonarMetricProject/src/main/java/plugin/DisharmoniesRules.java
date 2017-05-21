/**
 *
 */
package main.java.plugin;

import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.plugins.java.Java;
import org.sonar.squidbridge.annotations.AnnotationBasedRulesDefinition;

import main.java.parser.Disharmony;

/**
 * @author Tomas Lestyan
 */
public class DisharmoniesRules implements RulesDefinition {

	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String REPOSITORY = "disharmoniesRepository";
	public static final RuleKey TEST_RULE_KEY = RuleKey.of(REPOSITORY, "test");
	private static final  DisharmoniesRulesLoader xmlLoader = new DisharmoniesRulesLoader();


	/* (non-Javadoc)
	 * @see org.sonar.api.server.rule.RulesDefinition#define(org.sonar.api.server.rule.RulesDefinition.Context)
	 */
	@Override
	public void define(Context context) {
		NewRepository repository = context.createRepository(REPOSITORY, "java").setName("Disharmonies Rules repository");
		new AnnotationBasedRulesDefinition(repository, Java.KEY).addRuleClasses(false, false, Arrays.asList(Checks.checkClasses()));
		repository.done();
		xmlLoader.loadDisharmonyRulesIntoRepository(repository, DisharmoniesPlugin.RULES_URL);
		repository.done();
		log.info("Disharmony rules loaded properly");
	}

	/**
	 * @return disharmony rules
	 */
	public static Collection<Disharmony> getXmlRules() {
		return DisharmoniesRulesLoader.getDisharmonyRules(DisharmoniesPlugin.RULES_URL);
	}
}

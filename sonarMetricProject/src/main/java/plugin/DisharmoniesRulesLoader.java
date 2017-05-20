/**
 * The MIT License (MIT)
 * Copyright (c) 2016 FI MUNI
 */
package main.java.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition.NewRepository;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

import main.java.disharmonies.parser.Disharmony;
import main.java.disharmonies.parser.DisharmonyParser;

/**
 * TODO
 * @author Tomas
 */
public class DisharmoniesRulesLoader extends RulesDefinitionXmlLoader{

	private static final Logger log = LoggerFactory.getLogger(DisharmoniesRulesLoader.class);

	/**
	 * @param repo
	 * @param rulesLocation
	 * @return
	 */
	public void loadDisharmonyRulesIntoRepository(NewRepository repo, URL rulesLocation) {
		try (InputStream is = rulesLocation.openStream()) {
			load(repo, is, Charset.defaultCharset());
		} catch (IOException e) {
			log.warn("Rules from xml not loaded properly", e);
		}
	}

	/**
	 * @param rulesLocation
	 * @param rules
	 * @return
	 */
	public Collection<Disharmony> getDisharmonyRules(URL rulesLocation) {
		Collection<Disharmony> rules = new ArrayList<>();
		try (InputStream is = rulesLocation.openStream()) {
			rules = DisharmonyParser.parse(is);
		} catch (Exception e) {
			log.warn("Rules from xml not loaded properly", e);
		}
		return rules;
	}
}

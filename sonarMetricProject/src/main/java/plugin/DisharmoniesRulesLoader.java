/**
 * The MIT License (MIT)
 * Copyright (c) 2016 FI MUNI
 */
package main.java.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition.NewRepository;

import main.java.parser.Disharmony;
import main.java.parser.DisharmonyParser;

import org.sonar.api.server.rule.RulesDefinitionXmlLoader;

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
		getAllFiles(rulesLocation).forEach(x -> {
			try (InputStream is = x.openStream()) {
				load(repo, is, Charset.defaultCharset());
			} catch (IOException e) {
				log.warn("Rules from xml not loaded properly", e);
			}
		});
	}

	/**
	 * @param rulesLocation
	 * @param rules
	 * @return
	 */
	public static Collection<Disharmony> getDisharmonyRules(URL rulesLocation) {
		Collection<Disharmony> rules = new ArrayList<>();
		getAllFiles(rulesLocation).forEach(x -> {
			try (InputStream is = x.openStream()) {
				rules.addAll(DisharmonyParser.parse(is));
			} catch (Exception e) {
				log.warn("Rules from xml not loaded properly", e);
			}
		});
		return rules;
	}

	private static Collection<URL> getAllFiles(URL location) {
		Collection<URL> paths = new ArrayList<>();
		URI uri = getUri(location);
		if (uri != null) {
			Map<String, String> env = new HashMap<>();
			env.put("create", "true");
			try (FileSystem fileSystem = FileSystems.newFileSystem(uri, env)){
				Files.list(Paths.get(uri))
				.filter(Files::isRegularFile)
				.map(x -> pathToUrl(x))
				.filter(Objects::nonNull)
				.forEach(x -> {
					paths.add(x);
				});	
			} catch (IOException e) {
				log.error("Rule files URLs not loaded", e);
			}
		}
		return paths;
	}

	/**
	 * @param location
	 * @param uri
	 * @return
	 */
	private static URI getUri(URL location) {
		URI uri = null;
		try {
			uri = location.toURI();
		} catch (URISyntaxException e) {
			log.error("Uri not created", e);
		}
		return uri;
	}

	/**
	 * @param x
	 * @return
	 * @throws MalformedURLException
	 */
	private static URL pathToUrl(Path x) {
		URL url = null;
		try {
			url = x.toUri().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}

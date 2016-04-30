package main.java.plugin;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.Extension;
import org.sonar.api.SonarPlugin;

/**
 * The plugin definition
 * TODO under construction
 *
 * @author Tomas Lestyan
 */
public class Plugin extends SonarPlugin {
	
	 /**
     * The logger object for the plugin.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Default constructor.
     */
    public Plugin() {
        super();
    }

    /**
     * Defines the plugin extensions: metrics, sensor and dashboard widget.
     *
     * @return the list of extensions for this plugin
     */
    public List<Class<? extends Extension>> getExtensions() {
    	log.info("Disharmonies checker plugin started.");
        return Arrays.asList(
            PluginMetrics.class,
            PluginSensor.class,
            Widget.class);
    }
}

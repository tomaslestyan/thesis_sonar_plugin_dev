package main.java.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

/**
 * Definition of used metrics
 * TODO
 * @author Tomas Lestyan
 */
@SuppressWarnings("rawtypes")
public class PluginMetrics implements Metrics {

    /**
     * The project name (as configured in the IDE).
     */
    public static final Metric LOC=
        new Metric.Builder(
            "lines_of_code",
            "Lines of code",
            Metric.ValueType.STRING)
            .setDescription("The number of the lines of code")
            .setQualitative(false)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    /**
     * Default constructor.
     */
    public PluginMetrics() {
        super();
    }

    /**
     * Defines the plugin metrics.
     *
     * @return the list of this plugin metrics
     */
    
	public List<Metric> getMetrics() {
    	List<Metric> metrics = new ArrayList<>();
    	metrics.add(LOC);
        return metrics;
    }
}

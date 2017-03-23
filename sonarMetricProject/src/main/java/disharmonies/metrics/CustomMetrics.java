package main.java.disharmonies.metrics;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public class CustomMetrics implements Metrics {

	public static final Metric<Serializable> METHOD_PROPERTIES = new Metric.Builder("methodProperties", "Method Properties", Metric.ValueType.DATA)
			.setDescription("Metric aggregates properties of the methods in class")
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();

	public static final Metric<Serializable> TEST = new Metric.Builder("test", "Test", Metric.ValueType.INT)
			.setDescription("Test metric")
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.create();

	/* (non-Javadoc)
	 * @see org.sonar.api.measures.Metrics#getMetrics()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Metric> getMetrics() {
		return Arrays.asList(METHOD_PROPERTIES, TEST);
	}

}

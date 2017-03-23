/**
 * 
 */
package main.java.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.ce.measure.Component;
import org.sonar.api.ce.measure.Component.Type;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.ce.measure.MeasureComputer.MeasureComputerDefinition.Builder;

import main.java.disharmonies.metrics.CustomMetrics;
import main.java.measures.MeasureJSon;

/**
 * @author Tomas
 *
 */
public class DisharmoniesComputer implements MeasureComputer {

	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see org.sonar.api.ce.measure.MeasureComputer#define(org.sonar.api.ce.measure.MeasureComputer.MeasureComputerDefinitionContext)
	 */
	@Override
	public MeasureComputerDefinition define(MeasureComputerDefinitionContext defContext) {
		Builder defBuilder = defContext.newDefinitionBuilder();
		defBuilder.setInputMetrics(CustomMetrics.METHOD_PROPERTIES.getKey());
		defBuilder.setOutputMetrics(CustomMetrics.TEST.getKey());
		return defBuilder.build();
	}

	/* (non-Javadoc)
	 * @see org.sonar.api.ce.measure.MeasureComputer#compute(org.sonar.api.ce.measure.MeasureComputer.MeasureComputerContext)
	 */
	@Override
	public void compute(MeasureComputerContext context) {
		Component component = context.getComponent();
		Type type = component.getType();
		if (type.equals(Component.Type.FILE)) {
			Iterable<Measure> childrenMeasures = context.getChildrenMeasures(CustomMetrics.METHOD_PROPERTIES.getKey());
			for (Measure measure : childrenMeasures) {
				main.java.measures.Builder measureBuilder = new main.java.measures.Builder();
				measureBuilder.fromString(measure.getStringValue());
				MeasureJSon disharmoniesMeasure = measureBuilder.build();
			}
		}
	}

}

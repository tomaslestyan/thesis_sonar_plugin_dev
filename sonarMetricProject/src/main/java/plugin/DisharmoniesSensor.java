package main.java.plugin;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;

import main.java.components.ComponentFactory;
import main.java.components.IComponent;
import main.java.disharmonies.BrainMethod;
import main.java.metrics.MetricsRegister;
import main.java.tresholds.ITresholds;
import main.java.tresholds.TresholdFactory;


/**
 * Disharmonies detection sensor
 * @author Tomas Lestyan
 */
@Phase(name = Phase.Name.POST)
public class DisharmoniesSensor implements Sensor {

	/** The file system of the analyzed project */
	private final FileSystem fileSystem;
	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final ITresholds tresholds;
	private Collection<IComponent> components;

	/**
	 * Constructor
	 * @param fileSystem
	 */
	public DisharmoniesSensor(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
		this.tresholds = TresholdFactory.getTresholds();
		components = ComponentFactory.getClassComponents();
	}

	/* (non-Javadoc)
	 * @see org.sonar.api.batch.sensor.Sensor#describe(org.sonar.api.batch.sensor.SensorDescriptor)
	 */
	@Override
	public void describe(SensorDescriptor descriptor) {
		// nothing to describe yet
	}

	/* (non-Javadoc)
	 * @see org.sonar.api.batch.sensor.Sensor#execute(org.sonar.api.batch.sensor.SensorContext)
	 */
	@Override
	public void execute(SensorContext context) {
		log.info("Disharmonies sensor statrted");	
		Iterable<InputFile> inputFiles = fileSystem.inputFiles(fileSystem.predicates().all());
		inputFiles.forEach(x ->{
			Stream<IComponent> componentsOfFile = components.stream().filter(c -> FilenameUtils.equalsNormalized( c.getSonarComponentID(), x.absolutePath()));
			componentsOfFile.forEach(z -> checkComponentForIssues(z, context, x));

		});
		log.info("Disharmonies sensor finished");
	}

	private void checkComponentForIssues(IComponent component, SensorContext context, InputFile file) {
		Map<String, Integer> measuresOfComponent = component.getMeasures();
		int loc = measuresOfComponent.getOrDefault(MetricsRegister.LOC.getKey(), -1);
		int cyclo = measuresOfComponent.getOrDefault(MetricsRegister.CYCLO.getKey(), -1);
		int noav = measuresOfComponent.getOrDefault(MetricsRegister.NOAV.getKey(), -1);
		int maxnesting = measuresOfComponent.getOrDefault(MetricsRegister.MAXNESTING.getKey(), -1);
		boolean locAnomaly = loc >= 25;
		boolean complexityAnomaly =  cyclo >= 10;
		boolean variablesAnomaly =  noav >= 5;
		boolean nestingAnomaly =  maxnesting >= 3;
		if (locAnomaly && complexityAnomaly && variablesAnomaly && nestingAnomaly) {
			NewIssue issue = context.newIssue()
					.forRule(RuleKey.of( DisharmoniesRules.REPOSITORY, new BrainMethod().getKey()));
			NewIssueLocation primaryLocation = issue.newLocation()
					.on(file)
					.message(String.format("Too high values of: loc = %s, cyclo = %s, noav = %s, maxnesting = %s", loc, cyclo, noav, maxnesting));
			primaryLocation.at(file.selectLine(component.getStartLine()));
			issue.at(primaryLocation);

			issue.save();
		}
		// recursive execution for child components
		Collection<IComponent> childComponents = component.getChildComponents();
		if (childComponents != null && !childComponents.isEmpty()) {
			childComponents.forEach(x -> checkComponentForIssues(x, context, file));
		} else {
			return;
		}
	}
}
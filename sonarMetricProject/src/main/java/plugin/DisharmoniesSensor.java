package main.java.plugin;

import java.util.Collection;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;

import main.java.framework.api.Database;
import main.java.framework.api.components.IComponent;
import main.java.tresholds.IThresholds;
import main.java.tresholds.ThresholdFactory;


/**
 * Disharmonies detection sensor
 * @author Tomas Lestyan
 */
@DependsUpon("squid")
public class DisharmoniesSensor implements Sensor {

	/** The file system of the analyzed project */
	private final FileSystem fileSystem;
	/** The logger object */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final IThresholds thresholds;
	private Collection<IComponent> components;

	/**
	 * Constructor
	 * @param fileSystem
	 */
	public DisharmoniesSensor(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
		this.thresholds = ThresholdFactory.getThresholds();
		components = Database.getClassComponents();
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
		DisharmoniesContextSingleton.getInstance().getRules().forEach((k, v) -> {
			if (DisharmonyChecker.checkDisharmony(v, component, thresholds)) {
				NewIssue issue = context.newIssue()
						.forRule(RuleKey.of( DisharmoniesRules.REPOSITORY, k));
				NewIssueLocation primaryLocation = issue.newLocation()
						.on(file)
						.message(v.getDescription());
				primaryLocation.at(file.selectLine(component.getStartLine()));
				issue.at(primaryLocation);

				issue.save();
			}
		});

		// recursive execution for child components
		Collection<IComponent> childComponents = component.getChildComponents();
		if (childComponents != null && !childComponents.isEmpty()) {
			childComponents.forEach(x -> checkComponentForIssues(x, context, file));
		} else {
			return;
		}
	}
}
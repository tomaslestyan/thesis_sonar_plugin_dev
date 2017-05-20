package main.java.plugin;

import java.io.File;
import java.util.Collection;

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

import main.java.disharmonies.parser.Disharmony;
import main.java.framework.api.MeasurementRepository;
import main.java.framework.api.components.ClassComponent;
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
	/** The thresholds from DB */
	private final IThresholds thresholds;
	Collection<Disharmony> xmlRules;

	/**
	 * Constructor
	 * @param fileSystem
	 */
	public DisharmoniesSensor(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
		this.thresholds = ThresholdFactory.getThresholds();
		this.xmlRules = DisharmoniesRules.getXmlRules();
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
		String module = context.module().key();
		log.info("Disharmonies sensor statrted for module: " + module);
		Collection<ClassComponent> components = MeasurementRepository.getClassComponents(module);
		components.forEach(z -> checkComponentForIssues(z, context));
		log.info("Disharmonies sensor finished for module: " + module);
	}

	/**
	 * Check if components for disharmonies
	 * @param component
	 * @param context
	 */
	private void checkComponentForIssues(IComponent component, SensorContext context) {
		xmlRules.forEach(x -> {
			if (DisharmonyChecker.checkDisharmony(x, component, thresholds)) {
				File componentFile = new File(component.getSonarFileKey());
				if (componentFile.exists()) {
					InputFile file = fileSystem.inputFile(fileSystem.predicates().is(componentFile));
					NewIssue issue = context.newIssue()
							.forRule(RuleKey.of( DisharmoniesRules.REPOSITORY, x.getKey()));
					NewIssueLocation primaryLocation = issue.newLocation()
							.on(file)
							.message(x.getMessage());
					primaryLocation.at(file.selectLine(component.getStartLine()));
					issue.at(primaryLocation);
					issue.save();
				} else {
					log.error(component + "not exists anymore");
				}
			}
		});

		// recursive execution for child components
		Collection<IComponent> childComponents = component.getChildComponents();
		if (childComponents != null && !childComponents.isEmpty()) {
			childComponents.forEach(x -> checkComponentForIssues(x, context));
		} else {
			return;
		}
	}
}
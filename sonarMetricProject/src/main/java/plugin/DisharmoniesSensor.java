package main.java.plugin;

import java.io.File;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.resources.Language;
import org.sonar.plugins.java.api.tree.Tree;

import main.java.tresholds.ITresholds;
import main.java.tresholds.TresholdFactory;
import main.java.visitors.FileVisitor;


/**
 * Disharmonies detection sensor
 * @author Tomas Lestyan
 */
public class DisharmoniesSensor implements Sensor {

	/** The file system of the analyzed project */
    private final FileSystem fileSystem;
    /** The logger object */
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ITresholds tresholds;

    /**
     * Constructor
     * @param fileSystem
     */
    public DisharmoniesSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
        this.tresholds = TresholdFactory.getTresholds();
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
		log.info("DIsharmonies sensor statrted");
		Set<Entry<Tree, File>> treeEntries = DisharmoniesContextSingleton.getInstance().getFileTrees().entrySet();
		for (Entry<Tree, File> entry : treeEntries) {
			InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(entry.getValue()));
			if (inputFile != null) {
				new FileVisitor(inputFile, entry.getKey(), context, tresholds).scan();
			}
		}
		tresholds.saveTresholdValues();
		log.info("Disharmonies sensor finished");
	}
}
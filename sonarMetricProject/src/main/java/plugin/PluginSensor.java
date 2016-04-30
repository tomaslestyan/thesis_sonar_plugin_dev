package main.java.plugin;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.Measure;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.java.DefaultJavaResourceLocator;
import org.sonar.java.JavaClasspath;
import org.sonar.java.Measurer;
import org.sonar.java.SonarComponents;
import org.sonar.java.filters.SuppressWarningsFilter;
import org.sonar.plugins.java.Java;
import org.sonar.plugins.java.JavaPlugin;
import org.sonar.plugins.java.JavaSquidSensor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import main.java.analyzers.AnalyzerException;
import main.java.analyzers.EclipseAnalyzer;
import main.java.model.ProjectInfo;

/**
 * IDE Metadata plugin sensor. It analyses project directory in search for
 * IDE metadata configuration files and extracts relevant information.
 *
 * @author jorge.hidalgo
 * @version 1.0
 */
public class PluginSensor implements Sensor {

    /**
     * The file system object for the project being analysed.
     */
    private final FileSystem fileSystem;

    /**
     * The logger object for the sensor.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor that sets the file system object for the
     * project being analysed.
     *
     * @param fileSystem the project file system
     */
    public PluginSensor(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    /**
     * Determines whether the sensor should run or not for the given project.
     *
     * @param project the project being analysed
     * @return always true
     */
    public boolean shouldExecuteOnProject(Project project) {
        // this sensor is executed on any type of project
        return true;
    }

    /**
     * Analyses project directory in search for IDE metadata configuration files
     * and extracts relevant information.
     *
     * @param project       the project being analysed
     * @param sensorContext the sensor context
     */
    @Override
    public void analyse(Project project, SensorContext sensorContext) {

        File rootDir = fileSystem.baseDir();

        log.info("Analysing project root in search for anomalies " + rootDir.getAbsolutePath());

        EclipseAnalyzer analyzer = new EclipseAnalyzer(rootDir);
        ProjectInfo projectInfo;

        try {
            projectInfo = analyzer.analyze();

            log.info("Analysis done");
            log.info("this is what we've found: " + projectInfo);

            saveMainInfo(sensorContext, projectInfo, project);

        } catch (AnalyzerException ae) {
            log.error("Error while running EclipseAnalyzer", ae);
        }
    }

    /**
     * Saves measures corresponding to main project information.
     *
     * @param sensorContext the sensor context
     * @param projectInfo   the project information bean
     */
    private void saveMainInfo(SensorContext sensorContext, ProjectInfo projectInfo, Project project) {

        log.debug("saving measures for main project information");

        Measurer javaMeasurer = new Measurer(fileSystem, sensorContext, true);
//        Settings settings = new Settings();
//		JavaClasspath javaClasspath = new JavaClasspath(project, settings, fileSystem);
//		DefaultJavaResourceLocator defaultJavaResourceLocator = new DefaultJavaResourceLocator(fileSystem, javaClasspath, new SuppressWarningsFilter());
		InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().and(fileSystem.predicates().hasLanguage(Java.KEY), fileSystem.predicates().hasType(InputFile.Type.MAIN)));
        Measure measure;

        measure = new Measure(PluginMetrics.LOC,  "test 100");
        sensorContext.saveMeasure(measure);

        log.debug("measures saved");
    }
}

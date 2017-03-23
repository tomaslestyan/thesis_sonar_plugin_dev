/**
 * The MIT License (MIT)
 * Copyright (c) 2016 Tomas Lestyan
 */
package main.java.visitors;

import java.util.Map.Entry;
import java.util.Set;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import main.java.disharmonies.metrics.CustomMetrics;
import main.java.disharmonies.parser.Disharmony;
import main.java.measures.Builder;
import main.java.measures.MeasureJSon;
import main.java.plugin.DisharmoniesContextSingleton;
import main.java.plugin.DisharmoniesRules;
import main.java.tresholds.ITresholds;

/**
 * Disharmonies file visitor
 * @author Tomas Lestyan
 */
public class FileVisitor extends BaseTreeVisitor {

	private final InputFile file;
	private final Tree tree;
	private final SensorContext context;
	private final DisharmoniesContextSingleton disharmoniesContext  = DisharmoniesContextSingleton.getInstance();
	private final ITresholds tresholds;

	/**
	 * Constructor
	 * @param file
	 * @param tree
	 * @param context
	 * @param tresholds
	 */
	public FileVisitor(InputFile file, Tree tree, SensorContext context, ITresholds tresholds) {
		this.file = file;
		this.tree = tree;
		this.context = context;
		this.tresholds = tresholds;

	}

	/**
	 * Scan the file
	 */
	public void scan() {
		scan(tree);
	}

	/* (non-Javadoc)
	 * @see org.sonar.plugins.java.api.tree.BaseTreeVisitor#visitClass(org.sonar.plugins.java.api.tree.ClassTree)
	 */
	@Override
	public void visitClass(ClassTree tree) {
		int line = tree.declarationKeyword().line();
		detectDisharmonies(tree, line);
		super.visitClass(tree);
	}

	/* (non-Javadoc)
	 * @see org.sonar.plugins.java.api.tree.BaseTreeVisitor#visitMethod(org.sonar.plugins.java.api.tree.MethodTree)
	 */
	@Override
	public void visitMethod(MethodTree tree) {
		int line = tree.openParenToken().line();
		detectDisharmonies(tree, line);
		Builder builder = new Builder();
		builder.forMetric(CustomMetrics.TEST.getKey());
		builder.addMeasureForMetod("test", 1, 100, "15");
		builder.addMeasureForMetod("test", 101, 110, "15");
		MeasureJSon testJson = builder.build();
		context.newMeasure()
		.forMetric(CustomMetrics.METHOD_PROPERTIES)
		.on(file)
		.withValue(testJson.toJSONString());
		builder = new Builder();
		builder.fromString(testJson.toJSONString());
	}

	/**
	 * Invoke disharmony detection on scanned file
	 * @param tree
	 * @param lineToReport
	 */
	private void detectDisharmonies(Tree tree, int lineToReport) {
		Set<Entry<String, Disharmony>> entrySet = disharmoniesContext.getRules().entrySet();
		for (Entry<String, Disharmony> entry : entrySet) {
			boolean detected = entry.getValue().disharmonyDetected(tree, tresholds);
			if (detected) {
				NewIssue newIssue = context.newIssue()
						.forRule(RuleKey.of(DisharmoniesRules.REPOSITORY, entry.getKey()));
				NewIssueLocation primaryLocation = newIssue.newLocation()
						.on(file)
						.at(file.selectLine(lineToReport))
						.message(entry.getValue().getName());
				newIssue.at(primaryLocation);
				newIssue.save();
			}
		}
	}
}

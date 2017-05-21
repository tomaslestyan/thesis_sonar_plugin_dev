/**
 *
 */
package main.java.disharmonies;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;

/**
 * Example in code disharmony class
 * @author Tomas Lestyan
 */
@Rule(key = BrainMethod.BRAIN_METHOD_KEY, name = BrainMethod.BRAIN_METHOD_NAME, description = BrainMethod.BRAIN_METHOD_DESCRIPTION)
public class BrainMethod extends BaseTreeVisitor implements IDisharmony {

	static final String BRAIN_METHOD_KEY = "BrainMethod";
	static final String BRAIN_METHOD_NAME = "Brain method";
	static final String BRAIN_METHOD_DESCRIPTION = "Brain Methods tend to centralize the functionality of a class, in the same way as a God Class centralizes the functionality of an entire subsystem, or sometimes even a whole system.";
	private JavaFileScannerContext context;

	/* (non-Javadoc)
	 * @see main.java.disharmonies.IDisharmony#getKey()
	 */
	@Override
	public String getKey() {
		return BRAIN_METHOD_KEY;
	}

	/* (non-Javadoc)
	 * @see main.java.disharmonies.IDisharmony#getName()
	 */
	@Override
	public String getName() {
		return BRAIN_METHOD_NAME;
	}

	/* (non-Javadoc)
	 * @see main.java.disharmonies.IDisharmony#getDescription()
	 */
	@Override
	public String getDescription() {
		return BRAIN_METHOD_DESCRIPTION;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.sonar.plugins.java.api.JavaFileScanner#scanFile(org.sonar.plugins.
	 * java.api.JavaFileScannerContext)
	 */
	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		// scan the java file tree
		scan(context.getTree());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.sonar.plugins.java.api.tree.BaseTreeVisitor#visitMethod(org.sonar.
	 * plugins.java.api.tree.MethodTree)
	 */
	@Override
	public void visitMethod(MethodTree tree) {
		super.visitMethod(tree);
	}

}

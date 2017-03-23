package main.java.disharmonies.metrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;

import main.java.visitors.ADisharmonyVisitor;

/**
 * Abstract class for metrics definitions
 * @author Tomas Lestyan
 */
public abstract class AMetric {

	 /** The logger object */
     Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Compare value to trehold  value
	 * @param value
	 * @param treshold
	 * @param operator
	 * @return <code>true</code> if given binary operation is evaluated as true, <code>false</code> otherwise
	 */
	protected boolean compareToTreshold(int value, int treshold, CompareOperator operator) {
		boolean result;
		switch (operator) {
		case GREATER_THAN:
			result = value > treshold;
			break;
		case GREATER_OR_EQUAL_TO:
			result = value >= treshold;
			break;
		case LOWER_THAN:
			result = value < treshold;
			break;
		case LOWER_OR_EQUAL_TO:
			result = value <= treshold;
			break;
		case EQUAL_TO:
			result = value == treshold;
			break;
		default:
			throw new IllegalArgumentException(operator + " is not valid compare operator. Use one of the following compare operators '<', '>', '<=', '>=' or '==' ");
		}
		return result;
	}

	/**
	 * @param tree
	 * @param visitor
	 * @return the result of the metric on given Java tree
	 */
	public int getResult(Tree tree, ADisharmonyVisitor visitor) {
		int result = 0;
		if (visitor != null) {
			switch (visitor.getScope()) {
			case METHOD:
				if (tree instanceof MethodTree) {
					visitor.scanMethod((MethodTree) tree);
					result = visitor.getResult();
				}
				break;
			case CLASS:
				if (tree instanceof ClassTree) {
					visitor.scanClass((ClassTree) tree);
					result = visitor.getResult();
				}
				break;
			default:
				if (tree instanceof MethodTree) {
					visitor.scanMethod((MethodTree) tree);
					result = visitor.getResult();
				} else if (tree instanceof ClassTree) {
					visitor.scanClass((ClassTree) tree);
					result = visitor.getResult();
				}
				break;
			}
		}
		return result;
	}

	// helper classes

	/**
	 * Binary compare operator enum
	 * @author Tomas Lestyan
	 */
	public enum CompareOperator {

		GREATER_THAN(">"), GREATER_OR_EQUAL_TO(">="), LOWER_THAN("<"), LOWER_OR_EQUAL_TO("<="), EQUAL_TO("==");

		/** The value of the enum */
		private final String value;

		/**
		 * Constructor
		 * @param text
		 */
		private CompareOperator(final String value) {
			this.value = value;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return value;
		}

		 /**
		 * @return the string value of the operator
		 */
		public String getValue() {
		        return value;
		    }

		 /**
		 * @param value
		 * @return get the operator for string value
		 * @throws IllegalArgumentException if not valid operator string given
		 */
		public static CompareOperator getOperator(String value) {
		        for(CompareOperator operator : values())
		            if (operator.getValue().equalsIgnoreCase(value)) {
		            	return operator;
		            }
		        throw new IllegalArgumentException();
		    }
	}

	public class Modifier {

		private String operator = null;
		private String parameter = null;

		/**
		 * Constructor
		 * @param value
		 */
		public Modifier(String value) {
			if (value != null) {
				try {
					Pattern OperatorPattern = Pattern.compile("([\\+\\-\\*/\\(\\)])");
					Pattern ParameterPattern = Pattern.compile("(\\d+)");
					Matcher m = OperatorPattern.matcher(value);
					if (m.find()) {
						operator = m.group(0);
					}
					m = ParameterPattern.matcher(value);
					if (m.find()) {
						parameter = m.group(0);
					}
				} catch (Exception e) {
					operator = null;
					parameter = null;
					log.warn(value + " is not valid modifier");
				}
			}
		}

		/**
		 * Modify the given value
		 * @param value
		 * @return the modified value
		 */
		public int modify(int value) {
			if ((operator == null ) || (parameter == null)) return value;
			int result = value;
			int param = 0;
			try {
				param = Integer.parseInt(parameter);
			} catch (NumberFormatException e) {
				log.warn(value + " not modified");
				return value;
			}
			// do the modify operation
			switch (operator) {
			case "/":
				result = value / param;
				break;
			case "*":
				result = value * param;
				break;
			case "+":
				result = value + param;
				break;
			case "-":
				result = value - param;
				break;
			default:
				// operator not recognized
				break;
			}
			return result;
		}
	}
}

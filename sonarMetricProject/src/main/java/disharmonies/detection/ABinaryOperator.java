package main.java.disharmonies.detection;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;

/**
 * Binary operator for disharmonies evaluation
 * @author Tomas Lestyan
 */
public abstract class ABinaryOperator implements IDisharmonyDetectionBlock {

	@XmlAnyElement(lax = true)
	private final Collection<Object> children = new ArrayList<>();

	/**
	 * Get the children operations or metrics
	 * @return the collection of child blocks of the operator
	 */
	public  Collection<IDisharmonyDetectionBlock> getChildren() {
		Collection<IDisharmonyDetectionBlock> result = new ArrayList<>();
		for (Object childObject: children) {
			if (childObject instanceof IDisharmonyDetectionBlock) {
				result.add((IDisharmonyDetectionBlock) childObject);
			}
		}
		return result;
	}

}

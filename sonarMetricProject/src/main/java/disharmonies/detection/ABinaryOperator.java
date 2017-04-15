package main.java.disharmonies.detection;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXBElement;
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
	 * @param <T>
	 * @return the collection of child blocks of the operator
	 */
	public  <T> Collection<IDisharmonyDetectionBlock> getChildren() {
		Collection<IDisharmonyDetectionBlock> result = new ArrayList<>();
		for (Object childObject: children) {
			@SuppressWarnings("unchecked")
			T value = ((JAXBElement<T>) childObject).getValue();
			if (value instanceof IDisharmonyDetectionBlock) {
				result.add((IDisharmonyDetectionBlock) value);
			}
		}
		return result;
	}

}
